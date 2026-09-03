package bee.ghostly.client.renderstate;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class DeadRenderState {
    public static final RenderStateDataKey<DeadRenderState> DEAD_RENDER_STATE = RenderStateDataKey.create(() -> "dead");

    public boolean isDead = false;

}
