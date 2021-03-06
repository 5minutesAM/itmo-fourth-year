package itmo.se.lab2

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

class GenericTrigonometryEvaluatorTest {
    @Nested
    inner class GenericTrigonometryEvaluatorUnitTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes sine`(
            x: Double,
            sin: Double,
            cos: Double,
            _tan: Double,
            _cot: Double,
            _sec: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ITrigonometryEvaluator::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(sin, GenericTrigonometryEvaluator(mockTrigEval).sin(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes tangent`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            _cot: Double,
            _sec: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ITrigonometryEvaluator::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(tan, GenericTrigonometryEvaluator(mockTrigEval).tan(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes cotangent`(
            x: Double,
            sin: Double,
            cos: Double,
            _tan: Double,
            cot: Double,
            _sec: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ITrigonometryEvaluator::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(cot, GenericTrigonometryEvaluator(mockTrigEval).cot(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes secant`(
            x: Double,
            sin: Double,
            cos: Double,
            _tan: Double,
            _cot: Double,
            sec: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ITrigonometryEvaluator::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(sec, GenericTrigonometryEvaluator(mockTrigEval).sec(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig-invalid.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            cot: Double,
            sec: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ITrigonometryEvaluator::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), doubleThat { !(it.isNaN() || it.isInfinite()) })).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x), doubleThat { it.isNaN() || it.isInfinite() })).thenReturn(Double.NaN)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), doubleThat { !(it.isNaN() || it.isInfinite()) }))
                .thenReturn(cos)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), doubleThat { it.isNaN() || it.isInfinite() }))
                .thenReturn(Double.NaN)

            Assertions.assertEquals(sin, GenericTrigonometryEvaluator(mockTrigEval).sin(x, eps), 1E-6)
            Assertions.assertEquals(tan, GenericTrigonometryEvaluator(mockTrigEval).tan(x, eps), 1E-6)
            Assertions.assertEquals(cot, GenericTrigonometryEvaluator(mockTrigEval).cot(x, eps), 1E-6)
            Assertions.assertEquals(sec, GenericTrigonometryEvaluator(mockTrigEval).sec(x, eps), 1E-6)
        }
    }

    @Nested
    inner class GenericTrigonometryEvaluatorIntegrationTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes sine`(
            x: Double,
            sin: Double,
            _cos: Double,
            _tan: Double,
            _cot: Double,
            _sec: Double,
            eps: Double
        ) {
            Assertions.assertEquals(sin, GenericTrigonometryEvaluator(TrigonometryEvaluator()).sin(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes tangent`(
            x: Double,
            _sin: Double,
            _cos: Double,
            tan: Double,
            _cot: Double,
            _sec: Double,
            eps: Double
        ) {
            Assertions.assertEquals(tan, GenericTrigonometryEvaluator(TrigonometryEvaluator()).tan(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes cotangent`(
            x: Double,
            _sin: Double,
            _cos: Double,
            _tan: Double,
            cot: Double,
            _sec: Double,
            eps: Double
        ) {
            Assertions.assertEquals(cot, GenericTrigonometryEvaluator(TrigonometryEvaluator()).cot(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig.csv"], numLinesToSkip = 1)
        fun `it computes secant`(
            x: Double,
            _sin: Double,
            _cos: Double,
            _tan: Double,
            _cot: Double,
            sec: Double,
            eps: Double
        ) {
            Assertions.assertEquals(sec, GenericTrigonometryEvaluator(TrigonometryEvaluator()).sec(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trig-invalid.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            cot: Double,
            sec: Double,
            eps: Double
        ) {
            Assertions.assertEquals(sin, GenericTrigonometryEvaluator(TrigonometryEvaluator()).sin(x, eps), 1E-6)
            Assertions.assertEquals(tan, GenericTrigonometryEvaluator(TrigonometryEvaluator()).tan(x, eps), 1E-6)
            Assertions.assertEquals(cot, GenericTrigonometryEvaluator(TrigonometryEvaluator()).cot(x, eps), 1E-6)
            Assertions.assertEquals(sec, GenericTrigonometryEvaluator(TrigonometryEvaluator()).sec(x, eps), 1E-6)
        }
    }
}