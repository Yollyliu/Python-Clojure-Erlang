%% @author youlin-liu


-module(exchange). 




-export([start/0, createSpawn/4, send/3, master/1,displayCalling/1]).

start() ->
%%   {ok, Source} = file:consult("/master/third term/comp6411/Project/Project_Liu_Youlin_40055006/calls.txt"),
  {ok, Source} = file:consult("calls.txt"),
  io:fwrite("\n** Calls to be made **\n"),
  displayCalling(Source),
%%    io:fwrite("\n"),
  Caller = #{},
  All_Calling = #{},
%%   createSpawn(Source).
  createSpawn(Source, Caller, Source, All_Calling).

createSpawn(Source, Caller, Rest, All_Calling) ->
	%% createSpawn(Source) ->
  if
    length(Rest) > 0 ->
      [First|Remain] = Rest,
      Name = element(1, First),
      Pid = spawn(calling, start, []),
      NewMap = Caller#{Name => Pid},
      Update_Calling = All_Calling#{Pid => Name},
      createSpawn(Source, NewMap, Remain, Update_Calling);
    true ->
      send(Source, Caller, All_Calling) 
%%  	master()
  end.

send(Source, Caller, All_Calling) ->

  if	  

%% 	  length(Source)>1 ->	
%% 
%% 	    [First|Reminder] = Source,
%% 	    {Caller, Callees} = First,
%% 	    Pid = spawn(calling, user, [self(), Caller, Callees]),
%% 	    register(Caller, Pid),
%% 	    createSpawn(Reminder);
    length(Source) > 0 ->
		
      [First|Rest] = Source,
      Caller_Name = element(1, First),
      Callee_Name = element(2, First),
      #{Caller_Name := Pid} = Caller,
      Pid ! {self(), {create, Caller_Name, Callee_Name, Caller}},
      send(Rest, Caller, All_Calling);
%%       length(Source) == 1 ->	
%% 
%% 	    [First] = Source,
%% 	    {Caller, Callees} = First,
%% 	    Pid = spawn(calling, user, [self(), Caller, Callees]),
%% 	    register(Caller, Pid),
    true ->
      io:fwrite("\n"),
      master(All_Calling)
  end.

master(All_Calling) ->
  receive
%% 		{Caller_PID, Caller_Name, intro, Callee_PID, Callee_Name, Now_Secs} ->
%% 			Callee_PID ! {self(),Caller_PID, Caller_Name, intro, Callee_PID, Callee_Name, Now_Secs},
%% 			master(); 
    {Sender, {Receiver, intro, TimeStamp}} ->
      #{Sender := Caller_Name } = All_Calling,
      #{Receiver := Callee_Name} = All_Calling,
      io:fwrite("~w received intro message from ~w [~w]\n", [Caller_Name, Callee_Name, TimeStamp]),
      master(All_Calling);
	  
%% 		{Caller_PID, Caller_Name, reply, Callee_PID, Callee_Name, Now_Secs} ->
%% 			io:fwrite("~p received intro message from ~w [~w]\n", [Caller_Name, Callee_Name, Now_Secs]),
%% 			Callee_PID ! {self(), Caller_PID, Caller_Name, reply, Callee_PID, Callee_Name, Now_Secs},
%% 			master(),
    {Sender, {Receiver, reply, TimeStamp}} ->
      #{Sender := Caller_Name } = All_Calling,
      #{Receiver := Callee_Name} = All_Calling,
      io:fwrite("~w received reply message from ~w [~w]\n", [Caller_Name, Callee_Name, TimeStamp]),
      master(All_Calling)
  
  after 1500 ->
    io:fwrite("\nMaster has received no replies for 1.5 seconds, ending...\n")
  
  end.


displayCalling(Source) ->

	if
	  length(Source)>0 ->	

	    [First|Remainder] = Source,
	    {Caller, Callees} = First,
	    io:fwrite("~p: ~p\n",[Caller, Callees]),
	    displayCalling(Remainder);
	  
	  true ->
		  io:format("\n")

	end.


