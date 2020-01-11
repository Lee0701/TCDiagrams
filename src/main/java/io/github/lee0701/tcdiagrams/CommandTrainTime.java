package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.common.utils.ParseUtil;
import com.bergerkiller.bukkit.tc.Permission;
import com.bergerkiller.bukkit.tc.properties.CartProperties;
import com.bergerkiller.bukkit.tc.properties.TrainProperties;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrainTime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length >= 1) {
            if(args[0].equals("version")) {
                return true;
            }
            if(!Permission.BUILD_STATION.handleMsg(sender, "You don't have permission to use that command.")) return true;

            if(args[0].equals("timetable") || args[0].equals("tt")) {
                if(args.length >= 2) {
                    TimeTable timeTable = TimeTable.TIME_TABLES.get(args[1]);

                    if(args.length < 3 || args[2].equals("info")) {

                    } else {
                        if(args[2].equals("addstop") || args[2].equals("as")) {
                            if(args.length >= 5) {
                                int index = parseStopIndex(args[3]);
                                String name = args[4];
                                if(index == Integer.MAX_VALUE) index = timeTable.getStops().size();
                                if(index > -1 && index <= timeTable.getStops().size()) {
                                    Stop stop = new Stop(name);
                                    timeTable.getStops().add(index, stop);
                                    sender.sendMessage(ChatColor.YELLOW + "New stop added!");
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "Position must be number in the list or first/last!");
                                }
                            }
                            return true;

                        } else if(args[2].equals("deletestop") || args[2].equals("ds")) {
                            if(args.length >= 4) {
                                int index = parseStopIndex(args[3]);
                                if(index == Integer.MAX_VALUE) index = timeTable.getStops().size()-1;
                                if(index > -1 && index < timeTable.getStops().size()) {
                                    timeTable.getStops().remove(index);
                                    sender.sendMessage(ChatColor.YELLOW + "Stop deleted!");
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "Position must be number in the list or first/last!");
                                }
                            }
                            return true;

                        } else if(args[2].equals("editstop") || args[2].equals("es")) {
                            if(args.length >= 6) {
                                int index = parseStopIndex(args[3]);
                                if(index == Integer.MAX_VALUE) index = timeTable.getStops().size()-1;
                                if(index > -1 && index < timeTable.getStops().size()) {
                                    long departTime = ParseUtil.parseTime(args[4]);
                                    long stopDuration = ParseUtil.parseTime(args[5]);

                                    Stop stop = timeTable.getStops().get(index);
                                    stop.setDepartTime(departTime);
                                    stop.setStopDuration(stopDuration);

                                    sender.sendMessage(ChatColor.YELLOW + "Stop times updated!");
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "Position must be number in the list or first/last!");
                                }
                            }
                            return true;

                        } else if(args[2].equals("start")) {
                            if(sender instanceof Player) {
                                TrainProperties train = CartProperties.getEditing((Player) sender).getTrainProperties();
                                Train.of(train.getTrainName()).startTimeTable(timeTable);
                                sender.sendMessage(ChatColor.YELLOW + "Time table started on train " + train.getTrainName());
                            }
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Please enter a name for the time table!");
                }
                return true;

            } else if(args[0].equals("addtimetable") || args[0].equals("addtt") || args[0].equals("att")) {
                if(args.length >= 2) {
                    String name = args[1];
                    TimeTable timeTable = new TimeTable(name);
                    TimeTable.TIME_TABLES.put(name, timeTable);

                    sender.sendMessage(ChatColor.YELLOW + "Created a new time table.");
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Please enter a name for the time table!");
                }
                return true;

            } else if(args[0].equals("deletetimetable") || args[0].equals("deltt") || args[0].equals("dtt")) {
                if(args.length >= 2) {
                    String name = args[1];
                    if(TimeTable.TIME_TABLES.containsKey(name)) {
                        TimeTable.TIME_TABLES.remove(name);
                        sender.sendMessage(ChatColor.YELLOW + "Deleted time table.");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "That time table does not exist!");
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Please enter a name for the time table!");
                }
                return true;

            } else if(args[0].equals("listtimetable") || args[0].equals("listtt") || args[0].equals("ltt")) {
                StringBuilder message = new StringBuilder(ChatColor.YELLOW + "List of time tables:" + ChatColor.WHITE);
                for(TimeTable timeTable : TimeTable.TIME_TABLES.values()) {
                    message.append("\n - ");
                    message.append(timeTable.getName());
                }
                sender.sendMessage(message.toString());
                return true;

            } else if(args[0].equals("trainsystem") || args[0].equals("ts")) {
                if(args.length >= 2) {
                    TrainSystem trainSystem = TrainSystem.TRAIN_SYSTEMS.get(args[1]);

                    if(args.length < 3 || args[2].equals("info")) {

                    } else if(args[2].equals("start")) {
                        trainSystem.start();
                        sender.sendMessage(ChatColor.YELLOW + "Started train system.");
                    } else if(args[2].equals("stop")) {
                        trainSystem.stop();
                        sender.sendMessage(ChatColor.YELLOW + "Stopped train system.");
                    } else if(args[2].equals("addrun") || args[2].equals("addr") || args[2].equals("ar")) {
                        if(args.length >= 5) {
                            int index = parseStopIndex(args[3]);
                            String name = args[4];
                            if(index == Integer.MAX_VALUE) index = trainSystem.getTrainRuns().size();
                            if(index > -1 && index <= trainSystem.getTrainRuns().size()) {
                                TrainRun trainRun = new TrainRun(name);
                                trainSystem.getTrainRuns().add(index, trainRun);
                                sender.sendMessage(ChatColor.YELLOW + "New run added!");
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "Position must be number in the list or first/last!");
                            }
                        }
                        return true;
                    }
                }
                return true;

            } else if(args[0].equals("addtrainsystem") || args[0].equals("addts") || args[0].equals("ats")) {
                if(args.length >= 2) {
                    String name = args[1];
                    TrainSystem trainSystem = new TrainSystem(name);
                    TrainSystem.TRAIN_SYSTEMS.put(name, trainSystem);

                    sender.sendMessage(ChatColor.YELLOW + "Created a new train system.");
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Please enter a name for the train system!");
                }
                return true;

            } else if(args[0].equals("listtrainsystem") || args[0].equals("listts") || args[0].equals("lts")) {
                StringBuilder message = new StringBuilder(ChatColor.YELLOW + "List of train systems:" + ChatColor.WHITE);
                for(TrainSystem trainSystem : TrainSystem.TRAIN_SYSTEMS.values()) {
                    message.append("\n - ");
                    message.append(trainSystem.getName());
                }
                sender.sendMessage(message.toString());
            }
        }
        return false;
    }

    public int parseStopIndex(String input) {
        int index;
        if(input.equals("first") || input.equals("f")) index = 0;
        else if(input.equals("last") || input.equals("l")) index = Integer.MAX_VALUE;
        else index = ParseUtil.parseInt(input, -1);
        return index;
    }

}
