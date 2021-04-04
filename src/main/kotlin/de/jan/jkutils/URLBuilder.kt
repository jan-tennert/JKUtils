package de.jan.jkutils

class URLBuilder(private var baseURL: String) {

    private val parameters = hashMapOf<String, String>()

    init {
        if(baseURL.endsWith("/")) baseURL = baseURL.substring(0, baseURL.length - 1)
    }

    fun addParameter(key: String, value: String) : URLBuilder {
        parameters[key] = value
        return this
    }

    fun removeParameter(key: String) : URLBuilder {
        parameters.remove(key)
        return this
    }

    fun clearParameters() : URLBuilder {
        parameters.clear()
        return this
    }

    fun build() : String {
        var url = baseURL
        if(parameters.size == 0) return url

        var index = 0
        for (parameter in parameters) {
            if(index == 0) {
                url += "?${parameter.key}=${parameter.value}"
            } else {
                url += "&${parameter.key}=${parameter.value}"
            }
            index++
        }
        return url
    }

}