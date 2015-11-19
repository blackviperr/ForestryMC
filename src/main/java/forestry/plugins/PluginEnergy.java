/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.plugins;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import cpw.mods.fml.common.SidedProxy;

import forestry.api.core.ForestryAPI;
import forestry.core.GuiHandlerBase;
import forestry.core.blocks.BlockBase;
import forestry.core.config.Constants;
import forestry.core.config.ForestryBlock;
import forestry.core.items.ItemBlockForestry;
import forestry.core.recipes.RecipeUtil;
import forestry.energy.GuiHandlerEnergy;
import forestry.energy.blocks.BlockEngine;
import forestry.energy.proxy.ProxyEnergy;
import forestry.energy.tiles.EngineDefinition;
import forestry.energy.tiles.TileEngineBiogas;
import forestry.energy.tiles.TileEngineClockwork;
import forestry.energy.tiles.TileEnginePeat;

@Plugin(pluginID = "Energy", name = "Energy", author = "SirSengir", url = Constants.URL, unlocalizedDescription = "for.plugin.energy.description")
public class PluginEnergy extends ForestryPlugin {

	@SidedProxy(clientSide = "forestry.energy.proxy.ProxyEnergyClient", serverSide = "forestry.energy.proxy.ProxyEnergy")
	public static ProxyEnergy proxy;

	@Override
	protected void registerItemsAndBlocks() {
		super.registerItemsAndBlocks();

		ForestryBlock.engine.registerBlock(new BlockEngine(Material.iron), ItemBlockForestry.class, "engine");
	}

	@Override
	public void preInit() {
		EngineDefinition definitionEnginePeat = new EngineDefinition(Constants.DEFINITION_ENGINE_PEAT_META, "forestry.EngineCopper", TileEnginePeat.class,
				PluginEnergy.proxy.getRenderDefaultEngine(Constants.TEXTURE_PATH_BLOCKS + "/engine_copper_"));
		((BlockBase) ForestryBlock.engine.block()).addDefinition(definitionEnginePeat);

		EngineDefinition definitionEngineBiogas = new EngineDefinition(Constants.DEFINITION_ENGINE_BIOGAS_META, "forestry.EngineBronze", TileEngineBiogas.class,
				PluginEnergy.proxy.getRenderDefaultEngine(Constants.TEXTURE_PATH_BLOCKS + "/engine_bronze_"));
		((BlockBase) ForestryBlock.engine.block()).addDefinition(definitionEngineBiogas);

		EngineDefinition definitionEngineClockwork = new EngineDefinition(Constants.DEFINITION_ENGINE_CLOCKWORK_META, "forestry.EngineClockwork", TileEngineClockwork.class,
				PluginEnergy.proxy.getRenderDefaultEngine(Constants.TEXTURE_PATH_BLOCKS + "/engine_clock_"));
		((BlockBase) ForestryBlock.engine.block()).addDefinition(definitionEngineClockwork);
	}

	@Override
	public void doInit() {
		((BlockBase) ForestryBlock.engine.block()).registerDefinitions();
	}

	@Override
	protected void registerRecipes() {
		super.registerRecipes();

		RecipeUtil.addRecipe(ForestryBlock.engine.getItemStack(1, Constants.DEFINITION_ENGINE_PEAT_META),
				"###",
				" X ",
				"YVY",
				'#', "ingotCopper",
				'X', "blockGlass",
				'Y', "gearCopper",
				'V', Blocks.piston);

		RecipeUtil.addRecipe(ForestryBlock.engine.getItemStack(1, Constants.DEFINITION_ENGINE_BIOGAS_META),
				"###",
				" X ",
				"YVY",
				'#', "ingotBronze",
				'X', "blockGlass",
				'Y', "gearBronze",
				'V', Blocks.piston);

		if (ForestryAPI.activeMode.getBooleanSetting("energy.engine.clockwork")) {
			RecipeUtil.addRecipe(ForestryBlock.engine.getItemStack(1, Constants.DEFINITION_ENGINE_CLOCKWORK_META),
					"###",
					" X ",
					"ZVY",
					'#', "plankWood",
					'X', "blockGlass",
					'Y', Items.clock,
					'Z', "gearCopper",
					'V', Blocks.piston);
		}
	}

	@Override
	public GuiHandlerBase getGuiHandler() {
		return new GuiHandlerEnergy();
	}
}
