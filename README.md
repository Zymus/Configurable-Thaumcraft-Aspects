Configurable-Thaumcraft-Aspects [1.6.4] v0.2
============================================

This mod allows users to configure Thaumcraft aspects for Items and Blocks that 
do not have any.

Requirements
============================================

Java 1.7+

Installation
============================================

Copy '''ConfigurableThaumcraftAspects-1.6.4-0.2.zip''' into your mods folder 
(.minecraft/mods/)

Configuration
============================================

1. Upon launching the client, this mod will create the necessary file and 
directory to use the mod.

When adding mods to be loaded, use the following format in 
.minecraft/config/ConfigurableThaumcraftAspects.xml file

    <?xml version="1.0" encoding="utf-8"?>
    <mods>
        <mod name="ModName" modConfigFilePath="/ModConfigName.txt" />
    </mods>
    
__name__
: the name of the mod that will be loaded. This can be anything you 
want, but it must match the file name mentioned in part 2.

__modConfigFilePath__
: the path, from the config directory, where the mod's config file is located.

2. To add aspects, create a new file in the
.minecraft/config/ConfigurableThaumcraftAspects folder, with the name of the
mod specified in part 1. For example, if you specified the name in part 1 as

    "TConstruct"

You would create the following XML file.

    .minecraft/config/ConfigurableThaumcraftAspects/TConstruct.xml

The format for the aspect config file is as follows:
    <?xml version="1.0" encoding="utf-8"?>
    <mod name="">
        <object id="" metaValues="">
            <aspect type="" researchPoints="" />
        </object>
    </mod>
    
Where:

    name: the name of the mod specified in part 1.
    id: the name of the object from the mod's config file.
    metaValues: the meta values for which to apply the specified aspects.
    type: the name of the aspect (ie. "ignis" not "fire")
    researchPoints: the amount of research points to be gained upon scanning.

Example:

    <?xml version="1.0" encoding="utf-8"?>
    <mod name="TConstruct">
        <object id="Stone Torch" metaValues="-1">
            <aspect type="terra" researchPoints="1" />
        </object>
    </mod>
    
What this does is for the "Stone Torch" item (specified in the TConstruct
config file), we're going to give the player 1000 Terra research points when 
they scan a Stone Torch, regardless of the meta value.

If we wanted to add more aspects upon scanning, we would just need to add 
another aspect element, like so:

    <?xml version="1.0" encoding="utf-8"?>
    <mod name="TConstruct">
        <object id="Stone Torch" metaValues="-1">
            <aspect type="saxum" researchPoints="1" />
            <aspect type="lux" researchPoints="1" />
        </object>
    </mod>

For objects with multiple meta values (like, the brownstone blocks from Tinker's 
workshop), we would specify the values like so:

    <?xml version="1.0" encoding="utf-8"?>
    <mod name="TConstruct">
        <object id="Speed Stone" metaValues="0,1,2,3,5">
            <aspect type="saxum" researchPoints="1" />
            <aspect type="volatus" researchPoints="1" />
        </object>
    </mod>

What this does is give Brownstone blocks, with meta values 0, 1, 2, 3, and 5 one
research point in saxum, and one in volatus.
__NOTE__
: Specifying the metaValues in this way will cause each meta value to count as 
one block. Meaning if you scan an item with metaValue 0 in this case, scanning 
that same block, with meta values 1, 2, 3, or 5 will not result in additional 
research points, as they are all considered the "same block".

If you would like to avoid this, and have have each block with different meta 
values scannable, you need to add each of them as separate objects.

    <?xml version="1.0" encoding="utf-8"?>
    <mod name="TConstruct">
        <object id="Speed Stone" metaValues="0">
            <aspect type="saxum" researchPoints="1" />
            <aspect type="volatus" researchPoints="1" />
        </object>
        <object id="Speed Stone" metaValues="1">
            <aspect type="saxum" researchPoints="1" />
            <aspect type="volatus" researchPoints="1" />
        </object>
        <object id="Speed Stone" metaValues="2">
            <aspect type="saxum" researchPoints="1" />
            <aspect type="volatus" researchPoints="1" />
        </object>
    </mod>

License (Affero GPL v3.0)
============================================
    Configurable Thaumcraft Aspects
    Copyright (C) 2014 Zymus

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
