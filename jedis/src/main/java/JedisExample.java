import redis.clients.jedis.*;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JedisExample {
    final static JedisPoolConfig poolConfig = buildPoolConfig();
    static JedisPool jedisPool = new JedisPool(poolConfig, "localhost");

    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public static void main(String[] args) throws InterruptedException {
        // init, default is localhost?
        Jedis jedis = new Jedis();

        // Strings
        jedis.set("events/city/rome", "32,15,223,828");
        String cachedResponse = jedis.get("events/city/rome");
        System.out.println(cachedResponse);

        // Lists
        jedis.lpush("queue#tasks", "firstTask");
        jedis.lpush("queue#tasks", "secondTask");
        String task = jedis.rpop("queue#tasks");
        System.out.println(task);
        System.out.println(jedis.rpop("queue#tasks"));

        // Sets
        jedis.sadd("nicknames", "nickname#1");
        jedis.sadd("nicknames", "nickname#2");
        jedis.sadd("nicknames", "nickname#1");

        Set<String> nicknames = jedis.smembers("nicknames");
        boolean exists = jedis.sismember("nicknames", "nickname#1");
        System.out.println(exists);

        // Hashes
        jedis.hset("user#1", "name", "Peter");
        jedis.hset("user#1", "job", "politician");
        String name = jedis.hget("user#1", "name");
        Map<String, String> fields = jedis.hgetAll("user#1");
        String job = fields.get("job");
        System.out.println(fields);
        System.out.println(job);

        // Sorted Sets
        Map<String, Double> scores = new HashMap<>();

        scores.put("PlayerOne", 3000.0);
        scores.put("PlayerTwo", 1500.0);
        scores.put("PlayerThree", 8200.0);

        scores.keySet().forEach(player -> {
            jedis.zadd("ranking", scores.get(player), player);
        });

        String player = jedis.zrevrange("ranking", 0, 1).iterator().next();
        long rank = jedis.zrevrank("ranking", "PlayerOne");
        System.out.println(player);
        System.out.println(rank);

        // transaction, do an example by using cli
        System.out.println("=====transaction=====");
        String friendsPrefix = "friends#";
        String userOneId = "4352523";
        String userTwoId = "5552321";

        jedis.watch("friends#deleted#" + userOneId);
        Transaction t = jedis.multi();
//        t.spop(friendsPrefix + userOneId);
//        t.spop(friendsPrefix + userTwoId);
//        t.spop("friends#deleted#" + userOneId);
        t.sadd(friendsPrefix + userOneId, userTwoId);
        t.sadd(friendsPrefix + userTwoId, userOneId);
        t.sadd("friends#deleted#" + userOneId, "lol");
        System.out.println(t.exec());
//        System.out.println(jedis.get);


        // pipeline
        System.out.println("=====pipeline=====");
        String userOneId2 = "4352523";
        String userTwoId2 = "4849888";

        Pipeline p = jedis.pipelined();
        p.sadd("searched#" + userOneId2, "paris");
        p.zadd("ranking", 126, userOneId2);
        p.zadd("ranking", 325, userTwoId2);
        Response<Boolean> pipeExists = p.sismember("searched#" + userOneId2, "paris");
        Response<Set<String>> pipeRanking = p.zrange("ranking", 0, -1);
        p.sync();

        Boolean exists2 = pipeExists.get();
        Set<String> ranking = pipeRanking.get();
        System.out.println(exists2);
        System.out.println(ranking);

        // Publish/Subscribe
        System.out.println("=====Publish/Subcribe=====");
        Thread thread = new Thread(() -> {
            Jedis jSubscriber = new Jedis();
            jSubscriber.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    // handle message
                    System.out.println("hi");
                    System.out.println(message);
                }
            }, "channel");
        });
        // default is false, if non-daemon finish, this will be finish
        // thread.setDaemon(true);
//        System.out.println(thread.isDaemon());
        thread.start();

        Jedis jPublisher = new Jedis();
        jPublisher.publish("channel", "test message");

        jedis.close();

        // pool
        try (Jedis jedisPool = JedisExample.jedisPool.getResource()) {
            // do operations with jedis resource
        }

        // cluster, still need to setup
        try (JedisCluster jedisCluster = new JedisCluster(new HostAndPort("localhost", 6379))) {
            // use the jedisCluster resource as if it was a normal Jedis resource
        } catch (IOException e) {}

    }


}
