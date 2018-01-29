package manuelperera.com.base.screen.presenter.recyclerview

import android.support.v7.util.DiffUtil
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import manuelperera.com.base.screen.presenter.AdapterPresenter
import java.util.*

abstract class RecyclerViewAdapterPresenter<V : RecyclerViewAdapterPresenterView, T : RecyclerViewAdapterItem> : AdapterPresenter<V>() {

    var listData: MutableList<T> = mutableListOf()
    private var mLoadingList: MutableList<T> = mutableListOf()
    private var mNetworkErrorList: MutableList<T> = mutableListOf()

    override fun init() {
        initLists()
        initialize()
        loadData()
    }

    override fun initWithoutLoad() {
        initLists()
        initialize()
    }

    fun bindItemClick(view: View, data: T) {
        addSubscription(RxView.clicks(view)
                .flatMapCompletable({ getItemClickCompletable(view, data) })
                .subscribe())
    }

    fun bindReloadDataObservable(reloadObservable: Observable<Any>) {
        addSubscription(reloadObservable
                .flatMap<Transaction<MutableList<T>>> { loadLoadingData() }
                .flatMap<DiffUtil.DiffResult>(calculateRecyclerViewDiffs())
                .doOnNext(showResults())
                .flatMap<DiffUtil.DiffResult> { load() }
                .subscribe(showResults()))
    }

    fun bindClearDataObservable(clearObservable: Observable<Any>) {
        addSubscription(clearObservable
                .flatMap<Transaction<MutableList<T>>> { loadEmptyData() }
                .flatMap<DiffUtil.DiffResult>(calculateRecyclerViewDiffs())
                .subscribe(showResults()))
    }

    protected fun load(): Observable<DiffUtil.DiffResult> =
            loadObservableData()
                    .flatMap<DiffUtil.DiffResult>(calculateRecyclerViewDiffs())

    protected fun showResults(): Consumer<DiffUtil.DiffResult> =
            Consumer { diffResult -> getDiffResultBinder(diffResult) }

    fun hasLoadingView(): Boolean =
            mLoadingList.isNotEmpty()

    protected fun loadLoadingData(): Observable<Transaction<MutableList<T>>> =
            Observable.just(Transaction(mLoadingList, TransactionStatus.SUCCESS))

    protected fun calculateRecyclerViewDiffs(): Function<Transaction<MutableList<T>>, Observable<DiffUtil.DiffResult>> =
            Function { transaction ->
                Observable.create<DiffUtil.DiffResult> { observer ->
                    observer.onNext(calculateRecyclerViewDiffResult(if (transaction.isSuccess()) transaction.data else mNetworkErrorList))
                    observer.onComplete()
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }

    private fun loadData() {
        addSubscription(load().subscribe(showResults()))
    }

    private fun loadObservableData(): Observable<Transaction<MutableList<T>>> =
            getLoadObservable().map<Transaction<MutableList<T>>>(addAdapterItemTypeToElements())

    private fun addAdapterItemTypeToElements(): Function<Transaction<MutableList<T>>, Transaction<MutableList<T>>> =
            Function { transaction ->
                val newTransaction: Transaction<MutableList<T>>

                if (transaction.isSuccess() && transaction.data != null) {
                    val list = ArrayList<T>()

                    transaction.data?.let { data ->
                        for (element in data) {
                            if (element.getType() != RecyclerViewAdapterItem.Type.HEADER && element.getType() != RecyclerViewAdapterItem.Type.ITEM && element.getType() != RecyclerViewAdapterItem.Type.EMPTY)
                                element.setType(RecyclerViewAdapterItem.Type.ITEM)

                            list.add(element)
                        }
                    }

                    transaction.data = list
                    newTransaction = transaction
                } else
                    newTransaction = Transaction(status = TransactionStatus.ERROR)

                newTransaction
            }

    private fun getDiffResultBinder(diffResult: DiffUtil.DiffResult) {
        view?.getDiffResultBinder(diffResult)
    }

    private fun initLists() {
        mLoadingList = getLoadingList()
        listData.addAll(mLoadingList)

        mNetworkErrorList = getNetworkErrorList()
    }

    private fun loadEmptyData(): Observable<Transaction<MutableList<T>>> =
            Observable.just(Transaction(mutableListOf(), TransactionStatus.SUCCESS))

    abstract fun getLoadingList(): MutableList<T>

    abstract fun getNetworkErrorList(): MutableList<T>

    abstract fun getLoadObservable(): Observable<Transaction<MutableList<T>>>

    abstract fun getItemClickCompletable(viewClicked: View, data: T): Completable

    abstract fun initialize()

    abstract fun calculateRecyclerViewDiffResult(newData: List<T>?): DiffUtil.DiffResult

}