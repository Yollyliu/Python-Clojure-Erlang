%% @author youlin-liu

-module(calling). 


-export([start/0, user/2, createSpawn/2,now_Time/0]).

user(Callees, All_Calling) ->
  if
%	  length(Callees) > 1 ->
%%   	[FirstCallee | Reminder ] = Callees,
%%   	Now_Secs = now_Time(),
%%    	Pid ! {self(),Caller_Name, intro, FirstCallee, FirstCallee, Now_Secs},
%       user(Pid, Caller_Name, Reminder);
    length(Callees) > 0 ->
      [First|Rest] = Callees,
      #{First := Pid} = All_Calling,
	  TimeStamp=now_Time(),
      Pid ! {self(), {intro, TimeStamp}},
      user(Rest, All_Calling);
	
%	length(Callees) == 1 ->
%%
%%   	[FirstCallee ] = Callees,
%% 	    Now_Secs = now_Time(),
%% 		Pid ! {self(), Caller_Name, intro, FirstCallee, FirstCallee,Now_Secs},
%%   	userReply(Caller_Name)
    true ->
      io:fwrite("")
  end.

start() ->	
  receive
    {Master, {create, Caller, Callee, All_Calling}} ->
      user(Callee, All_Calling),
      createSpawn(Master, Caller)
  
  end.

createSpawn(Master,Caller) ->
  receive
%	  {Pid, Callee_PID, Callee_Name, intro, Caller_PID, Caller_Name, Now_Secs} ->
%% 			Pid ! {self(), Caller_Name, reply, Callee_PID, Callee_Name, Now_Secs},
%% 			userReply(Caller_Name);
    {Sender, {intro, TimeStamp}} ->
      random:seed(erlang:now()),
      Time = random:uniform(100),
      timer:sleep(Time),
      Sender ! {self(), {reply, TimeStamp}},
      Master ! {self(), {Sender, intro, TimeStamp}},
      createSpawn(Master,Caller);
%% 		{Pid, Callee_PID, Callee_Name, reply,  Caller_PID, Caller_Name, Now_Secs} ->
%% 			Pid ! {self(), Caller_Name, reply_received, Callee_PID, Callee_Name, Now_Secs},
%% 			userReply(Caller_Name)
    {Sender, {reply, TimeStamp}} ->
      random:seed(erlang:now()),
      Time = random:uniform(100),
      timer:sleep(Time),
      Master ! {self(), {Sender, reply, TimeStamp}},
      createSpawn(Master,Caller)
  
    after 1000 ->
      io:fwrite("\nProcess ~w has received no calls for 1 second, ending...\n",[Caller])
  end.


now_Time() ->
  {MegaSecs,Secs,MicroSecs} = now(),
  (MicroSecs).