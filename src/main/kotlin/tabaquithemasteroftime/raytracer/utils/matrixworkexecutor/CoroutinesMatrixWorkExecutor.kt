package tabaquithemasteroftime.raytracer.utils.matrixworkexecutor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * [MatrixWorkExecutor] implemented using coroutines.
 * @param partsCount count of coroutines
 * @param dispatcher coroutine dispatcher
 */
class CoroutinesMatrixWorkExecutor(
    private val partsCount: Int,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : MatrixWorkExecutor {

    override fun invoke(
        width: Int,
        height: Int,
        block: (Int, Int) -> Unit
    ) {
        runBlocking(dispatcher) {
            val partSize = width / partsCount
            repeat(partsCount) { index ->
                launch {
                    val startI = index * partSize
                    val endI = if (index == partsCount - 1) {
                        width - 1
                    } else {
                        startI + partSize
                    }
                    for (i in startI..endI) {
                        for (j in 0 until height) {
                            block.invoke(i, j)
                            // yield() is a good idea in theory and a bad idea at practice
                            // yield()
                        }
                    }
                }
            }
        }
    }
}