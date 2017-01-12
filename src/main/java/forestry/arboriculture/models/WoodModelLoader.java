package forestry.arboriculture.models;

import java.util.ArrayList;
import java.util.List;
import forestry.core.config.Constants;
import forestry.core.utils.ModelUtil;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition.MissingVariantException;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.VariantList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public enum WoodModelLoader implements ICustomModelLoader {
	INSTANCE;

	public static final List<String> validFiles = new ArrayList<>();
	public boolean isEnabled = false;

	static {
		validFiles.add("door");
		validFiles.add("double_slab");
		validFiles.add("fence");
		validFiles.add("fence_gate");
		validFiles.add("log");
		validFiles.add("planks");
		validFiles.add("slab");
		validFiles.add("stairs");
	}

	// NOOP, handled in loader
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if (!isEnabled || !(modelLocation instanceof ModelResourceLocation)
				|| !modelLocation.getResourceDomain().equals(Constants.MOD_ID)
				|| !modelLocation.getResourcePath().contains("arboriculture")) {
			return false;
		}
		String path = modelLocation.getResourcePath();
		for (String validFile : validFiles) {
			if (path.endsWith(validFile)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		ModelResourceLocation variant = (ModelResourceLocation) modelLocation;
		ModelBlockDefinition definition = ModelUtil.getModelBlockDefinition(variant);
		try {
			VariantList variants = definition.getVariant(variant.getVariant());
			return new SimpleModel(variant, variants);
		} catch (MissingVariantException e) {
			if (definition.hasMultipartData()) {
				return new MultipartModel(new ResourceLocation(variant.getResourceDomain(), variant.getResourcePath()),
						definition.getMultipartData());
			}
			throw e;
		}
	}
}
