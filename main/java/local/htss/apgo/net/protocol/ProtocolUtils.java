package local.htss.apgo.net.protocol;

import local.htss.apgo.net.clients.ClientNetwork;
import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.auth.AuthInitPacket05;
import local.htss.apgo.net.protocol.handshake.Frame00PublicKey;
import local.htss.apgo.net.protocol.handshake.Frame01Aes;
import local.htss.apgo.net.protocol.shared.EncryptedFrame;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;
import local.htss.apgo.net.protocol.shared.SupportCodec;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ProtocolUtils {
    public static boolean isClient(NetworkThread networkThread) {
        return networkThread instanceof ClientNetwork;
    }
    public static boolean encryptOutcome(Class<?extends ProtocolFrame> clazz) {
        return clazz.getAnnotation(FrameMeta.TransportPolicy.class).encrypt();
    }
    public static FrameMeta.Identifiers getIds(Class<?> clazz) {
        return clazz.getAnnotation(FrameMeta.Identifiers.class);
    }
    public static ProtocolFrame decodeIntermediate(DataInputStream dataInputStream, NetworkThread thread) throws IOException {
        @SuppressWarnings("unchecked")
        Class<?extends ProtocolFrame> frameClazz = (Class<? extends ProtocolFrame>) NetworkRegistry.lookupId(dataInputStream.readLong());
        if(frameClazz == null) {
            System.out.println("No id found");
            return null;
        }
        ProtocolFrame protocolFrame;
        try {
            protocolFrame = frameClazz.getDeclaredConstructor(NetworkThread.class).newInstance(thread);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Instantion error");
            e.printStackTrace();
            return null;
        }
        protocolFrame.decode(dataInputStream);
        return protocolFrame;
    }
    public static class NetworkRegistry {
        private static HashMap<Long, Class<?extends ProtocolFrame>> frameMapping;
        private static long idxNoId = 5000;
        static {
            frameMapping = new HashMap<>();
            register(Frame00PublicKey.class);
            register(EncryptedFrame.class);
            register(Frame01Aes.class);
            register(AuthInitPacket05.class);
        }

        public static void register(Class<?extends ProtocolFrame> clazz) {
            FrameMeta.Identifiers identifiers = getIds(clazz);
            if(identifiers == null) {
                System.out.println("no id");
                getFrameMapping().put(idxNoId++, clazz);
            } else {
                getFrameMapping().put(identifiers.prefix(), clazz);
            }
        }

        public static Class<?extends SupportCodec> lookupId(long id) {
            return getFrameMapping().get(id);
        }

        public static HashMap<Long, Class<? extends ProtocolFrame>> getFrameMapping() {
            return frameMapping;
        }

        public static void setFrameMapping(HashMap<Long, Class<? extends ProtocolFrame>> frameMapping) {
            NetworkRegistry.frameMapping = frameMapping;
        }
    }
}
