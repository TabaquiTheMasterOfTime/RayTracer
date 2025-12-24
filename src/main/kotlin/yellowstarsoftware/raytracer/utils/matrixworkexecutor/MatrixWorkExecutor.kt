package yellowstarsoftware.raytracer.utils.matrixworkexecutor

/**
 * Entity for execution matrices of work.
 */
interface MatrixWorkExecutor {

    /**
     * Invokes [block] for each (i, j)
     * in 0..<[width] * 0..<[height].
     * Invocations might be concurrent.
     */
    fun invoke(
        width: Int,
        height: Int,
        block: (Int, Int) -> Unit
    )
}