    package mars.mips.instructions.customlangs;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.util.*;
    import mars.mips.instructions.*;
    import java.util.Random;

public class MinecraftAssembly extends CustomAssembly{
     @Override
    public String getName(){
        return "Minecraft Assembly";
    }
@Override
    public String getDescription(){
        return "Assembly language to let your computer control Ki";
    }
    @Override
    protected void populate(){
        instructionList.add(
            new BasicInstruction("pickup $t0, 100",
            "change the item count of slot register by an immediate value",
            BasicInstructionFormat.I_FORMAT,
            "001000 fffff 00000 ssssssssssssssss",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                    
                     int[] operands = statement.getOperands();
                     int add2 = operands[1] << 16 >> 16;
                    if (add2 > 0){
                     if(operands[0] >= 9 && operands[0] <= 12){
                        int add1 = RegisterFile.getValue(operands[0]);
                        int sum = add1 + add2;
                        RegisterFile.updateRegister(operands[0], sum);
                        SystemIO.printString("You picked up " + sum + " items to slot " + (operands[0] - 8) + "\n"); //$t0 is slot 1
                     } else {
                        SystemIO.printString("Cannot pick up items to that slot\n"); 
                     }
                    } else {
                        SystemIO.printString("Cannot pick up a non positive number of items\n");
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("swap $t0, $t1",
            "swap the item counts of two inventory slots",
            BasicInstructionFormat.I_FORMAT,
            "001000 fffff sssss 0000000000000000",
                                
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                    
                     int[] operands = statement.getOperands();
                     if((operands[0] >= 9 && operands[0] <= 12) && (operands[1] >= 9 && operands[1] <= 12)){
                        int temp = RegisterFile.getValue(operands[0]);
                        int temp1 = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[0], temp1);
                        RegisterFile.updateRegister(operands[1], temp);
                        SystemIO.printString("Swapped slots " + (operands[0] - 8) + " and " + (operands[1] - 8) + "\n"); //$t0 is slot 1
                     } else {
                        SystemIO.printString("Can only swap items in slots 1-4\n"); 
                     }
                
                  }
        }));
        instructionList.add(
            new BasicInstruction("gc $t0",
            "Prints the item count of a slot",
            BasicInstructionFormat.I_FORMAT,
            "000000 fffff 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                        int[] operands = statement.getOperands();
                    if (operands[0] >= 9 && operands[0] <= 12){
                     int val = RegisterFile.getValue(operands[0]);
                     SystemIO.printString("There are " + val + " items in slot " + (operands[0] - 8) + "\n");
                    } else {
                        if (operands[0] > 0){
                            SystemIO.printString("There is one item in that slot\n");
                        } else {
                            SystemIO.printString("There are no items in that slot\n");
                        }
                        
                        
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("gd $t0",
            "Prints the durability of an item",
            BasicInstructionFormat.I_FORMAT,
            "000000 fffff 00001 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                        int[] operands = statement.getOperands();
                    if ((operands[0] >= 13 && operands[0] <= 15)){
                     int val = RegisterFile.getValue(operands[0]);
                     SystemIO.printString("Item in slot " + (operands[0] - 8) + " has " + val + "durability left\n");
                    } else if (operands[0] >= 24 && operands[0] <= 25) {
                        int val = RegisterFile.getValue(operands[0]);
                        SystemIO.printString("Item in slot " + (operands[0] - 20) + " has " + val + "durability left\n");
                    } else {
                        SystemIO.printString("Cannot check durability for this slot\n");
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("dm $t1,-100",
            "drops a specified number of material from a slot",
            BasicInstructionFormat.I_FORMAT,
            "001000 fffff 00010 ssssssssssssssss",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int[] operands = statement.getOperands();
                     int add2 = operands[1] << 16 >> 16;
                    if (add2 > 0){
                     if(operands[0] >= 9 && operands[0] <= 12){
                        int add1 = RegisterFile.getValue(operands[0]);
                        add2 = operands[1] << 16 >> 16;
                        int sum = add1 - add2;
                        RegisterFile.updateRegister(operands[0], sum);
                        if (RegisterFile.getValue(operands[0]) < 0){
                            RegisterFile.updateRegister(operands[0], 0);
                        }
                        SystemIO.printString("You dropped " + add2 + " items from slot " + (operands[0] - 8) + "\n"); //$t1 is slot 1
                     } else {
                        SystemIO.printString("Cannot drop items from that slot with this command\n"); 
                     }
                    } else {
                        SystemIO.printString("Cannot drop a non positive amount of items\n"); 
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("dsm $t1",
            "drops a single material from specified slot",
            BasicInstructionFormat.I_FORMAT,
            "001000 fffff 00001 ssssssssssssssss",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int[] operands = statement.getOperands();
                     if(operands[0] >= 9 && operands[0] <= 12){
                        if (operands[0] > 0) {
                        int sum = RegisterFile.getValue(operands[0]) - 1;
                        RegisterFile.updateRegister(operands[0], sum);
                        SystemIO.printString("you dropped an item from slot " + (operands[0] - 8) + "\n"); //$t0 is slot 1
                        } else {
                            SystemIO.printString("no items to drop\n"); 
                        }
                     } else {
                        SystemIO.printString("Cannot drop items from that slot with this command\n"); 
                     }
                  }
        }));
        instructionList.add(
            new BasicInstruction("MoveX 100",
            "Move character along the x-plane",
            BasicInstructionFormat.I_FORMAT,
            "001000 00000 00001 ffffffffffffffff",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int[] operands = statement.getOperands();
                     int move = operands[0] << 16 >> 16;
                     int pos = RegisterFile.getValue(16) + move;
                     RegisterFile.updateRegister(16, move);
                     if (move == 0){
                        SystemIO.printString("You moved nowhere\n");
                     } else if (move > 0){
                        SystemIO.printString("You moved " + move + " blocks to the right\n");
                     } else {
                        SystemIO.printString("You moved " + Math.abs(move) + " blocks to the left\n"); 
                     }
                  }
        }));
        instructionList.add(
            new BasicInstruction("MoveY 100",
            "Move character along the y-plane",
            BasicInstructionFormat.I_FORMAT,
            "001000 00000 00010 ffffffffffffffff",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int[] operands = statement.getOperands();
                     int move = operands[0] << 16 >> 16;
                     int pos = RegisterFile.getValue(17) + move;
                     RegisterFile.updateRegister(17, move);
                     if (move == 0){
                        SystemIO.printString("You moved nowhere\n");
                     } else if (move > 0){
                        SystemIO.printString("You moved " + move + " blocks up\n");
                     } else {
                        SystemIO.printString("You moved " + Math.abs(move) + " blocks down\n");
                     }
                  }
        }));
        instructionList.add(
            new BasicInstruction("MoveZ -100",
            "Move character along the z-plane",
            BasicInstructionFormat.I_FORMAT,
            "001000 00000 00011 ffffffffffffffff",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int[] operands = statement.getOperands();
                     int move = operands[0] << 16 >> 16;
                     int pos = RegisterFile.getValue(18) + move;
                     RegisterFile.updateRegister(18, move);
                     if (move == 0){
                        SystemIO.printString("You moved nowhere\n");
                     } else if (move > 0){
                        SystemIO.printString("You moved " + move + " blocks forward\n");
                     } else {
                        SystemIO.printString("You moved " + Math.abs(move) + " blocks backward\n");
                     }
                  }
        }));
        instructionList.add(
                new BasicInstruction("craft $t0, $t1",
            	 "craft a random item if there is a sufficient value to be taken from both input  registers",
                BasicInstructionFormat.I_FORMAT,
                "10000 fffff sssss 0000000000000000",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                    if((operands[0] >= 9 && operands[0] <= 12) && (operands[1] >= 9 && operands[1] <= 12)){
                        if ((operands[0] == operands[1]) && (RegisterFile.getValue(operands[0]) < 3)){
                            SystemIO.printString("Insufficient amount of materials!\n");
                        }else if(RegisterFile.getValue(operands[0]) > 1 && RegisterFile.getValue(operands[1]) > 1){
                           int n = RegisterFile.getValue(operands[0]) - 2;
                           RegisterFile.updateRegister(operands[0], n);
                           n = RegisterFile.getValue(operands[1]) - 2;
                           RegisterFile.updateRegister(operands[1], n);
                           Random random = new Random();
                           int item = random.nextInt(5);
                            //item = roll;
                            // SystemIO.printString("Random roll: " + roll + "\n");

                            RegisterFile.updateRegister(8, item);

                            // print item received depending on roll
                            switch (RegisterFile.getValue(8)){ // 2 = register $v0 
                            case 0:
                                SystemIO.printString("You crafted a sword!\n");
                                RegisterFile.updateRegister(13, 25);
                                break;
                            case 1:
                                SystemIO.printString("You crafted a pickaxe!\n");
                                RegisterFile.updateRegister(14, 25);
                                break;
                            case 2:
                                SystemIO.printString("You crafted an axe!\n");
                                RegisterFile.updateRegister(15, 25);
                                break;
                            case 3:
                                SystemIO.printString("You crafted a shield!\n");
                                RegisterFile.updateRegister(24, 25);
                                break;
                            case 4:
                                SystemIO.printString("You crafted a golden apple!\n");
                                int curval = RegisterFile.getValue(25) + 1;
                                RegisterFile.updateRegister(25, curval);
                                break;
                            default:
                                SystemIO.printString("You crafted nothing!\n");
                            }
                        } else {
                            SystemIO.printString("Insufficient amount of materials!\n");
                    }
                } else {
                    SystemIO.printString("Can only craft using items in slots 1-4\n");
                }
            }
        }));
        instructionList.add(
            new BasicInstruction("slot -1",
            "switches item slot that is held in hand",
            BasicInstructionFormat.I_FORMAT,
            "001001 00000 00000 ffffffffffffffffs",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                    int[] operands = statement.getOperands();
                    int n = operands[0] << 16 >> 16;
                    if (n >= 5 && n <= 9){
                        RegisterFile.updateRegister(8, n);
                        SystemIO.printString("Held slot switched to: " + n + "\n");
                    } else {
                        SystemIO.printString("Only slots 5-9 can be equiped\n");
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("DropHeldItem",
            "Drops current held item/last crafted item",
            BasicInstructionFormat.I_FORMAT,
            "100000 00000 00000 0000000000000000",
                                
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     switch (RegisterFile.getValue(8)){ // 2 = register $v0 
                            case 0:
                                if (RegisterFile.getValue(13) > 0){
                                SystemIO.printString("You dropped your sword!\n");
                                RegisterFile.updateRegister(13, 0);
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                            case 1:
                                if (RegisterFile.getValue(14) > 0){
                                SystemIO.printString("You dropped your pickaxe!\n");
                                RegisterFile.updateRegister(14, 0);
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                            case 2:
                                if (RegisterFile.getValue(15) > 0){
                                SystemIO.printString("You dropped your axe!\n");
                                RegisterFile.updateRegister(15, 0);
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                            case 3:
                                if (RegisterFile.getValue(24) > 0){
                                SystemIO.printString("You dropped your shield!\n");
                                RegisterFile.updateRegister(24, 0);
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                            case 4:
                                if (RegisterFile.getValue(25) > 0){
                                SystemIO.printString("You dropped a golden apple!\n");
                                int curval = RegisterFile.getValue(25) - 1;
                                RegisterFile.updateRegister(25, curval);
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                            default:
                                SystemIO.printString("You dropped nothing!\n");
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("Use",
            "Uses held item",
            BasicInstructionFormat.I_FORMAT,
            "100000 00001 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     Random random = new Random();
                     int dura = random.nextInt(3) + 1;
                     switch (RegisterFile.getValue(8)){ // 2 = register $v0 
                        case 0:
                            if (RegisterFile.getValue(13) > 0){
                                SystemIO.printString("You swung your sword!\n");
                                int newdura = RegisterFile.getValue(13) - dura;
                                RegisterFile.updateRegister(13, newdura);
                                SystemIO.printString("Your sword lost " + dura + " durability\n");
                                if (RegisterFile.getValue(13) <= 0){
                                    SystemIO.printString("You sword broke!\n");
                                }
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                        case 1:
                            if (RegisterFile.getValue(14) > 0){
                                SystemIO.printString("You swung your pickaxe!\n");
                                int newdura = RegisterFile.getValue(14) - dura;
                                RegisterFile.updateRegister(14, newdura);
                                SystemIO.printString("Your pickaxe lost " + dura + " durability\n");
                                if (RegisterFile.getValue(14) <= 0){
                                    SystemIO.printString("You pickaxe broke!\n");
                                }
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                        case 2:
                            if (RegisterFile.getValue(15) > 0){
                                SystemIO.printString("You swung your axe!\n");
                                int newdura = RegisterFile.getValue(15) - dura;
                                RegisterFile.updateRegister(15, newdura);
                                SystemIO.printString("Your axe lost " + dura + " durability\n");
                                if (RegisterFile.getValue(15) <= 0){
                                    SystemIO.printString("You axe broke!\n");
                                }
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                        case 3:
                            if (RegisterFile.getValue(24) > 0){
                                SystemIO.printString("Your shield blocked something!\n");
                                int newdura = RegisterFile.getValue(24) - dura;
                                RegisterFile.updateRegister(24, newdura);
                                SystemIO.printString("Your shield lost " + dura + " durability\n");
                                if (RegisterFile.getValue(24) <= 0){
                                    SystemIO.printString("You shield broke!\n");
                                }
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                        case 4:
                            if (RegisterFile.getValue(25) > 0){
                                SystemIO.printString("You ate a golden apple!\n");
                                int newdura = RegisterFile.getValue(25) - 1;
                                RegisterFile.updateRegister(25, newdura);
                                if (RegisterFile.getValue(25) <= 0){
                                    SystemIO.printString("You ate all your golden apples!\n");
                                }
                                } else {
                                    SystemIO.printString("Hand is empty\n");
                                }
                                break;
                        default:
                           SystemIO.printString("You swung your fist!\n");
                    }
                  }
        }));
        instructionList.add(
            new BasicInstruction("Coordinates",
            "Prints player coordinates",
            BasicInstructionFormat.I_FORMAT,
            "100000 00010 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int x = RegisterFile.getValue(16);
                     int y = RegisterFile.getValue(17);
                     int z = RegisterFile.getValue(18);
                     SystemIO.printString("Your position: (" + x + ", " + y + ", " + z + ")\n");
                  }
        }));
        instructionList.add(
            new BasicInstruction("SetHome",
            "Saves current position as your home",
            BasicInstructionFormat.I_FORMAT,
            "001001 11111 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int x = RegisterFile.getValue(16);
                     int y = RegisterFile.getValue(17);
                     int z = RegisterFile.getValue(18);
                     RegisterFile.updateRegister(19, x);
                     RegisterFile.updateRegister(20, y);
                     RegisterFile.updateRegister(21, z);
                     SystemIO.printString("Home set to: (" + x + ", " + y + ", " + z + ")\n");
                  }
        }));
        instructionList.add(
            new BasicInstruction("Home",
            "Teleports player back to home coordinates",
            BasicInstructionFormat.I_FORMAT,
            "001001 01111 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     int x = RegisterFile.getValue(19);
                     int y = RegisterFile.getValue(20);
                     int z = RegisterFile.getValue(21);
                     RegisterFile.updateRegister(16, x);
                     RegisterFile.updateRegister(17, y);
                     RegisterFile.updateRegister(18, z);
                     SystemIO.printString("Teleported to home at: (" + x + ", " + y + ", " + z + ")\n");
                  }
        }));
        instructionList.add(
            new BasicInstruction("Initialize",
            "Initializes all starting values",
            BasicInstructionFormat.I_FORMAT,
            "000000 00000 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     RegisterFile.updateRegister(8, 0);
                     RegisterFile.updateRegister(9, 0);
                     RegisterFile.updateRegister(10, 0);
                     RegisterFile.updateRegister(11, 0);
                     RegisterFile.updateRegister(12, 0);
                     RegisterFile.updateRegister(13, 0);
                     RegisterFile.updateRegister(14, 0);
                     RegisterFile.updateRegister(15, 0);
                     RegisterFile.updateRegister(16, 0);
                     RegisterFile.updateRegister(17, 0);
                     RegisterFile.updateRegister(18, 0);
                     RegisterFile.updateRegister(19, 0);
                     RegisterFile.updateRegister(20, 0);
                     RegisterFile.updateRegister(21, 0);
                     RegisterFile.updateRegister(24, 0);
                     RegisterFile.updateRegister(25, 0);
                  }
        }));
        instructionList.add(
            new BasicInstruction("Kill",
            "resets all values",
            BasicInstructionFormat.I_FORMAT,
            "000000 00000 11111 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     RegisterFile.updateRegister(8, 0);
                     RegisterFile.updateRegister(9, 0);
                     RegisterFile.updateRegister(10, 0);
                     RegisterFile.updateRegister(11, 0);
                     RegisterFile.updateRegister(12, 0);
                     RegisterFile.updateRegister(13, 0);
                     RegisterFile.updateRegister(14, 0);
                     RegisterFile.updateRegister(15, 0);
                     RegisterFile.updateRegister(16, 0);
                     RegisterFile.updateRegister(17, 0);
                     RegisterFile.updateRegister(18, 0);
                     RegisterFile.updateRegister(24, 0);
                     RegisterFile.updateRegister(25, 0);
                     SystemIO.printString("You died!\n");
                  }
        }));
        instructionList.add(
            new BasicInstruction("mine",
            "If player has a pickaxe, mines a block and increases items and decreases pickaxe durability",
            BasicInstructionFormat.I_FORMAT,
            "100000 00011 00000 0000000000000000",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                     if (RegisterFile.getValue(14) > 0){
                        Random rand = new Random();
                        int loot = rand.nextInt(4) + 9;
                        int num = RegisterFile.getValue(loot) + 1;
                        RegisterFile.updateRegister(loot, num);
                        num = RegisterFile.getValue(14) - 3;
                        RegisterFile.updateRegister(14, 3);
                        if (RegisterFile.getValue(14) > 0){
                            SystemIO.printString("You gained one item to slot " + (loot - 8) + "\n");
                            SystemIO.printString("Pickaxe lost 3 durability\n");
                        } else {
                            SystemIO.printString("You gained one item to slot " + (loot - 8) + "\n");
                            SystemIO.printString("Your pickaxe broke!\n");
                        }
                     } else {
                        SystemIO.printString("You don't have a pickaxe!\n");
                     }
                  }
        }));
        instructionList.add(
            new BasicInstruction("Pearl 100",
            "teleport to a random location with higher variance dependant on immediate value",
            BasicInstructionFormat.I_FORMAT,
            "100000 00100 00000 ffffffffffffffff",
            new SimulationCode()
               {
                    public void simulate(ProgramStatement statement) throws ProcessingException
                    {
                    int[] operands = statement.getOperands();
                    Random random = new Random();
                    int xchange = random.nextInt(operands[0]);
                    int ychange = random.nextInt(operands[0]);
                    int zchange = random.nextInt(operands[0]);
                    int isneg = random.nextInt(2);
                    int n = 0;
                    if (isneg == 0){
                        n = RegisterFile.getValue(16) - xchange;
                        RegisterFile.updateRegister(16, n);
                    } else {
                        n = RegisterFile.getValue(16) + xchange;
                        RegisterFile.updateRegister(16, n);
                    }
                    isneg = random.nextInt(2);
                    if (isneg == 0){
                        n = RegisterFile.getValue(17) - ychange;
                        RegisterFile.updateRegister(17, n);
                    } else {
                        n = RegisterFile.getValue(17) + ychange;
                        RegisterFile.updateRegister(17, n);
                    }
                    isneg = random.nextInt(2);
                    if (isneg == 0){
                        n = RegisterFile.getValue(17) - zchange;
                        RegisterFile.updateRegister(17, n);
                    } else {
                        n = RegisterFile.getValue(17) + zchange;
                        RegisterFile.updateRegister(17, n);
                    }
                    int x = RegisterFile.getValue(16);
                    int y = RegisterFile.getValue(17);
                    int z = RegisterFile.getValue(18);
                    SystemIO.printString("You pearled to: (" + x + ", " + y + ", " + z + ")\n");
                  }
        }));
    }
}

