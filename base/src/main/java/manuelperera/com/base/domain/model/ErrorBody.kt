package manuelperera.com.base.domain.model

import com.google.gson.annotations.SerializedName

class ErrorBody(@SerializedName("status_code")
                var code: Int,
                @SerializedName("status_message")
                var message: String)