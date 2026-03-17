import java.util.*;

public class MultiLevelCache {

    static class VideoData {
        String id;
        String content;

        VideoData(String id, String content) {
            this.id = id;
            this.content = content;
        }
    }

    private final int L1_CAPACITY = 10000;
    private final int L2_CAPACITY = 100000;
    private final int PROMOTION_THRESHOLD = 3;

    private final LinkedHashMap<String, VideoData> L1 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
                    return size() > L1_CAPACITY;
                }
            };

    private final LinkedHashMap<String, VideoData> L2 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
                    return size() > L2_CAPACITY;
                }
            };

    private final Map<String, VideoData> L3 = new HashMap<>();
    private final Map<String, Integer> accessCount = new HashMap<>();

    public MultiLevelCache() {
    }

    public void putVideo(String id, String content) {
        VideoData video = new VideoData(id, content);
        L3.put(id, video);
    }

    public String getVideo(String id) {
        accessCount.put(id, accessCount.getOrDefault(id, 0) + 1);

        if (L1.containsKey(id)) {
            return "L1 HIT: " + L1.get(id).content;
        }

        if (L2.containsKey(id)) {
            VideoData video = L2.get(id);
            if (accessCount.get(id) >= PROMOTION_THRESHOLD) {
                L1.put(id, video);
            }
            return "L2 HIT: " + video.content;
        }

        if (L3.containsKey(id)) {
            VideoData video = L3.get(id);
            L2.put(id, video);
            return "L3 HIT: " + video.content;
        }

        return "MISS";
    }

    public void invalidate(String id) {
        L1.remove(id);
        L2.remove(id);
        L3.remove(id);
        accessCount.remove(id);
    }

    public String getStats() {
        return "L1 size: " + L1.size() +
                ", L2 size: " + L2.size() +
                ", L3 size: " + L3.size();
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        cache.putVideo("video_123", "data1");
        cache.putVideo("video_456", "data2");

        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));

        System.out.println(cache.getStats());
    }
}