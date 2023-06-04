/*
Coderiders Academy Task 3.

The MIT License (MIT)

Copyright (c) 2015 OpenFin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package edu.codeinside.coderiders;

import com.google.devtools.common.options.OptionsParser;
import edu.codeinside.coderiders.app.Application;
import edu.codeinside.coderiders.app.argparse.ApplicationOptions;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Collections;

/**
 * Main app launcher class
 */
public class Task03Application {

    /**
     *
     */
    private static final Logger
            LOGGER = LogManager.getLogger(Class.class.getName());

    /**
     * Implements main application functionality.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(ApplicationOptions.class);
        parser.parseAndExitUponError(args);
        ApplicationOptions options = parser.getOptions(ApplicationOptions.class);

        // check for user need help
        if (options.help) {
            printUsage(parser);
            return;
        }

        // verbose option detected - can log more!
        if (options.verbose) {
            LOGGER.info("Verbosity level increased.");
        } else {
            Configurator.setLevel("GLOBAL", Level.OFF);
        }

        LOGGER.info("Creating application...");

        Application application = new Application();

        LOGGER.info("Creating application... done");

        LOGGER.info("Running application...");
        application.run();

        LOGGER.warn("Have a nice day :)");
        System.out.println("\nHave a nice day :)");
    }

    /**
     * @param parser
     */
    private static void printUsage(OptionsParser parser) {
        System.out.println("Usage: java -jar task03-0.1.jar OPTIONS");
        System.out.println(parser.describeOptions(Collections.emptyMap(),
                OptionsParser.HelpVerbosity.LONG));
    }

}
