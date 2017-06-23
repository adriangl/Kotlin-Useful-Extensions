package ramdom.kotlin_useful_extensions

import android.os.Parcel
import android.os.Parcelable

private val doElseStub: () -> Unit = {}

/**
 * Based on a given [predicate], execute one of the given functions [doIf] or [doElse].
 */
fun wonder(predicate: () -> Boolean,
           doIf: () -> Unit,
           doElse: () -> Unit = doElseStub) {
    predicate.invoke().wonder(doIf, doElse)
}

/**
 * Return true if the current object is null.
 */
fun <T : Any?> T.isNull(): Boolean = this == null

/**
 * Execute the given [block] if the current object is null.
 */
inline fun <T> T.letIfNull(crossinline block: (T?) -> Unit) = {
    if (this == null) block(this)
}

/**
 * Execute [doIf] or [doElse] depending if the value of this [Boolean] is true or false.
 */
inline fun Boolean.wonder(
        doIf: () -> Unit,
        doElse: () -> Unit
) {
    if (this) doIf.invoke() else doElse.invoke()
}

/**
 * Check if the given [CharSequence] contains one of the given [elements].
 */
fun CharSequence.containsSome(vararg elements: CharSequence, ignoreCase: Boolean = false): Boolean =
        elements.filter { charSequence -> contains(charSequence, ignoreCase) }.isNotEmpty()

/**
 * Return a [Parcelable.Creator] from the current class which extens [Parcelable].
 */
inline fun <reified T : Parcelable> createParcel(
        crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }
