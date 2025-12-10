# Mars-LE-extension
Overview:
My language is a custom assembly language that allows for the playing and potentially coding of a mini-minecraft-esque game. The commands I made are more focused on simple gameplay, however they can be used to scale a larger and more complex version. Many of the commands build off of each other as there is a defined register set at the bottom that documents the roles of each register. For intended use, $t1-$t9 are meant to represent player hotbar slots with 1-4 representing slots that can hold materials and the rest holding items crafted by the user. Additionally there is a position system stored in saved registers 0-5, and $t0 represents equipped hotbar slot. Basically all of the commands in this language revolve around this system and have interplay.
Basic: 

Pick up item:
Pickup $t0, 100
001000 fffff 00000 ssssssssssssssss
	Changes the item count of slot register by an immediate value

Swap items:
Swap $t0, $t1
001000 fffff sssss 0000000000000000
	Swaps the item counts of two inventory slots

Get item count:
Gc $t0
000000 fffff 00000 0000000000000000
	Prints the item count of a slot

Get durability:
Gd $t0
000000 fffff 00001 0000000000000000
	Prints the durability of an item

Drop material:
Dm $t1
001000 fffff 00010 0ssssssssssssssss
	Drops a specified number of material from a slot

Drop single material:
Dms $t1
001000 fffff 00001 ssssssssssssssss
	Drops a single material from a slot

Move x position:
MoveX 100
001000 00000 00001 ssssssssssssssss
	Move character along the x-axis

Move y position:
MoveY 100
001000 00000 00010 ssssssssssssssss
	Move character along the y-axis



Move z position:
MoveZ 100
001000 00000 00011 ssssssssssssssss
	Move character along the z-axis

Craft item:
Craft $t0, $t1
100000 fffff sssss 0000000000000000
	Craft a random item if there is a sufficient value to be taken from both input registers

Unique:

Change slot:
Slot 1
001001 00000 00000 ssssssssssssssss
Switches item slot that is held in hand

Drop held item:
DropHeldItem
100000 00000 00000 0000000000000000
Drops current held item/last crafted item

Use held item:
Use
100000 00001 00000 0000000000000000
	Uses held item

Print coordinates:
Coordinates
100000 00010 00000 0000000000000000
Prints player coordinates

Set home:
SetHome
001001 11111 00000 0000000000000000
	Saves current position as your home

Teleport home:
Home
001001 01111 00000 0000000000000000
Teleports player back to home coordinates



Initialize values:
Initialize
000000 00000 00000 0000000000000000
Initializes all starting values

Kill player:
Kill
000000 00000 11111 0000000000000000
Resets all values

Mine material:
Mine
100000 00011 00000 0000000000000000
If player has a pickaxe, mines a block and increases items and decreases pickaxe durability

Throw ender pearl:
Pearl 100
100000 00100 00000 ssssssssssssssss
Teleport to a random location with a higher variance dependant on immediate value

Register Set:
$t0 = tracks slot equipped in hand
$t1 - $t4 = Material holding registers
$t5-$t7 = tool item registers
$s0-$s2 = Current coordinate registers
$s3-$s5 = Saved coordinate registers
$t8-$t9 = tool item registers
















Examples:
1.
	Initialize                             // initializes values
	Pickup $t1, 4		// pick up commands pick up items to the slots
Pickup $t2, 4
Craft $t1, $t2		// uses materials to craft a random item
gc $t1			// gc of slot 1 will print 2 now that materials have been used		
2.
	Initialize
	moveX 10            // move commands change positions
	moveY -15
Coordinates         // coordinates printed as (10, -15, 0)
	setHome		// home set to current coordinates
	Kill			// player killed taking back to 0,0,0
Coordinates			// (0, 0, 0) is printed as it is now players position after dying
	Home					// player teleports home
	Coordinates			// coordinates back to (10, -15, 0)
3.
	Initialize
	Pickup $t1, 4          // pick up commands pick up items to the slots
Pickup $t2, 4
Craft $t1, $t2			// uses materials to craft a random item
gd $t5		// we will assume a sword is crafted and durability is printed at 25
Use		// use swings the sword and decreases durability by random 1-3
gd $t5			// prints new durability 22
