package bee.ghostly.mixin.client;

import bee.ghostly.client.renderstate.DeadRenderState;
import bee.ghostly.util.GhostUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super  S>>
	extends EntityRenderer<T, S> implements RenderLayerParent<S, M> {

	@Shadow
	public abstract Identifier getTextureLocation(S state);

	private LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Inject(at = @At(value = "HEAD"), method = "getRenderType", cancellable = true)
	private void renderType(S state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing, CallbackInfoReturnable<RenderType> cir) {
		//if (state.getDataOrDefault(DeadRenderState.DEAD_RENDER_STATE, new DeadRenderState()).isDead) cir.setReturnValue(RenderTypes.entityShadow(getTextureLocation(state)));
	}

	@Inject(at = @At(value = "HEAD"), method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", cancellable = true)
	private void gee(T entity, S state, float partialTicks, CallbackInfo ci) {
		if (entity instanceof Player player && GhostUtil.isGhost(player)) {
			DeadRenderState deadRenderState = new DeadRenderState();
			deadRenderState.isDead = true;

			state.setData(DeadRenderState.DEAD_RENDER_STATE, deadRenderState);

		}
	}


	@Inject(at = @At(value = "HEAD"), method = "getShadowRadius(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)F", cancellable = true)
	private void shadow(S state, CallbackInfoReturnable<Float> cir) {
		if (state.getDataOrDefault(DeadRenderState.DEAD_RENDER_STATE, new DeadRenderState()).isDead) cir.setReturnValue(0f);
	}


	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"), method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
	private void makeGray(SubmitNodeCollector instance, Model model, Object o, PoseStack poseStack, RenderType renderType, int x, int y, int tintedColor, TextureAtlasSprite textureAtlasSprite, int i, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, Operation<Void> original) {



		if (o instanceof EntityRenderState state && state.getDataOrDefault(DeadRenderState.DEAD_RENDER_STATE, new DeadRenderState()).isDead) original.call(instance, model, o, poseStack, renderType, x, y, ARGB.multiplyAlpha(tintedColor, .5f), textureAtlasSprite, i, crumblingOverlay);

		original.call(instance, model, o, poseStack, renderType, x, y, tintedColor, textureAtlasSprite, i, crumblingOverlay);

	}
}