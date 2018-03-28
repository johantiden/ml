package com.github.johantiden.ml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johantiden.ml.evolutionary.RandomCandidateSupplier;
import com.github.johantiden.ml.evolutionary.doubles.DoublesTwoBreeder;
import com.github.johantiden.ml.util.Maths;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.jimage.awt.ImageConverter;
import com.github.johantiden.ml.evolutionary.Evolutionary;
import com.github.johantiden.ml.evolutionary.doubles.DoublesOneBreeder;
import com.github.johantiden.ml.evolutionary.worm.WormFitnessFunction;
import com.github.johantiden.ml.evolutionary.worm.WormPainter;
import com.github.johantiden.ml.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@Configuration
public class Main implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final int DEFAULT_PORT = 5000;
    public static final int DOWNSCALE = 16;

    public static void main(String[] args) {
        log.info("Started Main.main");
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> container.setPort(getPort());
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        int p = port != null ?
                Integer.valueOf(port) :
                DEFAULT_PORT;
        return p;
    }

    private static String getEnvOrDie(String name) {
        final String env = System.getenv(name);
        if (env == null) {
            String message = "Environment variable " + name + " must be set";
            log.error(message, new IllegalArgumentException(message));
            System.out.println(message);
            System.exit(1);
        }
        return env;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
               .setRedirectStrategy(new DefaultRedirectStrategy())
               .setRetryHandler(new StandardHttpRequestRetryHandler())
               .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler(executor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor executor() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean
    public ImageService imageService() {
        return new ImageService();
    }

    public static JTImage getTargetImage(int downscale) {
        try {
            URL resource = ImageService.class.getResource("/images/monalisa.jpg");
            File file = new File(resource.toURI());
            boolean exists = Files.exists(file.toPath());
            if (!exists) {
                throw new RuntimeException("Could not find file " + file);
            }
            BufferedImage read = ImageIO.read(file);
            return ImageConverter.toJTImage(read, downscale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Painter painter() {
        return new WormPainter();
    }

    @Bean
    public Evolutionary evolver() {
        JTImage targetImage = getTargetImage(DOWNSCALE);
        RandomCandidateSupplier randomCandidateSupplier = randomCandidateSupplier(targetImage);
        final Evolutionary treeDataEvolutionary = new Evolutionary(
                new DoublesOneBreeder(painter().getChunkSize(), randomCandidateSupplier),
                new DoublesTwoBreeder(painter().getChunkSize()),
                new WormFitnessFunction(targetImage, painter()),
                randomCandidateSupplier
        );

        executor().execute(() -> {
            while (true) {
                treeDataEvolutionary.iterate();
            }
        });

        return treeDataEvolutionary;
    }

    private RandomCandidateSupplier randomCandidateSupplier(JTImage targetImage) {

        int width = targetImage.getWidth();

        int chunkSize = painter().getChunkSize();
        return () -> {
            List<Double> doubles = new ArrayList<>(chunkSize);

            for (int i = 0; i < chunkSize -4; i++) {
                doubles.add(Maths.random(width));
            }

            doubles.add(Maths.random());
            doubles.add(Maths.random());
            doubles.add(Maths.random());
            doubles.add(Maths.random());
            return doubles;
        };

    }

}
