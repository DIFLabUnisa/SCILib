package it.unisa.di.dif.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.File;

public class CHILogger {
    public Logger log;
    public final String TIME_STAT= "<<RT>>";
    public Constant constant = Constant.getInstance();
    public static CHILogger instance = null;

    public static CHILogger getInstance() {
        if(instance == null) {
            instance = new CHILogger();
        }
        return instance;
    }

    private CHILogger() {
        File logFolder = new File(constant.getAppdir() + File.separator + "logs");
        if(!logFolder.exists()){
            logFolder.mkdirs();
        }

        String logFilePath=logFolder.getAbsolutePath()+File.separator+"dif.log";
        String logFilePatternPath=logFolder.getAbsolutePath()+File.pathSeparator+"dif-%d{MM-dd-yy}.log.gz";

        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.ALL)
                .setConfigurationName("SCIConfiguration");

        LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t] %-5level: %msg%n");
        ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));
        AppenderComponentBuilder appenderBuilder = builder.newAppender("rolling", "RollingFile")
                .addAttribute("fileName", logFilePath)
                .addAttribute("filePattern", logFilePatternPath)
                .add(layoutBuilder)
                .addComponent(triggeringPolicy);
        builder.add(appenderBuilder);

        if(constant.isWriteMessageLogOnConsole()){
            appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
                    ConsoleAppender.Target.SYSTEM_OUT);
            appenderBuilder.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
            builder.add( appenderBuilder );
        }

        // create the new logger
        builder.add( builder.newLogger( constant.getStringLogName(), Level.ALL )
                .add( builder.newAppenderRef( "rolling" ) )
                .addAttribute( "additivity", false ) );

        builder.add( builder.newRootLogger( Level.ALL )
                .add( builder.newAppenderRef( "rolling" ) ) );

        LoggerContext ctx = Configurator.initialize(builder.build());

        log = LogManager.getLogger(constant.getStringLogName());
    }
}
