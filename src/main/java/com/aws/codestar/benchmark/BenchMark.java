package com.aws.codestar.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMark {

    @State(Scope.Thread)
    public static class MyValues {
        public static final Logger logger = Logger.getLogger(BenchMark.class.getName());
    }

    @Benchmark
    public void factorial_4000(Blackhole bh) {
        BigInteger result = factorial(new BigInteger("4000"));
        bh.consume(result);
    }

    private BigInteger factorial(BigInteger num) {
        BigInteger total;
        if (num.equals(new BigInteger("1"))) {
            return new BigInteger("1");
        } else {
            total = num.multiply(factorial(num.subtract(new BigInteger("1"))));
        }
        return total;
    }

    public void main() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchMark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();

        // prints out the results to console.
        MyValues.logger.log(Level.INFO, "RESULTS_OF_BENCHMARK");
        MyValues.logger.log(Level.INFO, " The Factorial benchmark has run " );
    }
}
