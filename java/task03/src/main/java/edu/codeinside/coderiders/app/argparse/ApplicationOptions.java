package edu.codeinside.coderiders.app.argparse;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Argument parser options definition class.
 */
public class ApplicationOptions extends OptionsBase {

    /**
     *  Help option
     */
    @Option(
            name = "help",
            abbrev = 'h',
            help = "show this help message and exit",
            defaultValue = "false"
    )
    public boolean help;

    /**
     *  Verbose output option
     */
    @Option(
            name = "verbose",
            abbrev = 'v',
            help = "be more verbose",
            category = "main",
            defaultValue = "false"
    )
    public boolean verbose;

}
