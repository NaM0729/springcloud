package com.test.nettydemo;

import com.test.nettydemo.tcp.server.DemoServer;
import com.test.nettydemo.millionlinks.Client;
import com.test.nettydemo.millionlinks.Server;
import com.test.nettydemo.udp.server.UdpServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Bean
    public DemoServer getHelloServer() {
        return new DemoServer();
    }

    @Bean
    public UdpServer getUdpServer() {
        return new UdpServer();
    }

    @Bean
    public Server getServer() {
        return new Server();
    }

    @Bean
    public Client getClient() {
        return new Client();
    }

    @Override
    public void run(String... args) throws Exception {
//        getHelloServer().start();

//        getUdpServer().run(8886);

//        new Server().start(8000, 100);

        getClient().start(8000, 100);
    }
}
