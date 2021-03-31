package de.jan.jkutils

infix fun Int.percentOf(total: Int) : Double {
    return (this * total) / 100.toDouble()
}

infix fun Int.percentOf(total: Long) : Double {
    return (this * total) / 100.toDouble()
}

infix fun Int.percentOf(total: Double) : Double {
    return (this * total) / 100.toDouble()
}

infix fun Int.percentOf(total: Float) : Double {
    return (this * total) / 100.toDouble()
}
