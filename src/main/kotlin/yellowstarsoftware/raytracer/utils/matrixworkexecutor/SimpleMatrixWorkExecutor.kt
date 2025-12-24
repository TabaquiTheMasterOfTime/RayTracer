package yellowstarsoftware.raytracer.utils.matrixworkexecutor

/**
 * Just executes work in the current thread.
 * You don't need parallelism if you are patient enough.
 */
object SimpleMatrixWorkExecutor : MatrixWorkExecutor {

    override fun invoke(
        width: Int,
        height: Int,
        block: (Int, Int) -> Unit
    ) {
        for (i in 0 until width)
        for (j in 0 until height) block.invoke(i, j)
    }
}