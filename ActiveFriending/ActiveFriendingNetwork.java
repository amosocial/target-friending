package ActiveFriending;

import java.util.ArrayList;

import influence.Network;

public class ActiveFriendingNetwork extends Network{
	
	public int t;
	public ArrayList<Integer> cfriends = new ArrayList<Integer>();
	
	public ActiveFriendingNetwork(String path, String type, int vertexNum) {
		super(path, type, vertexNum);
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void get_rrsets(ArrayList<ArrayList<Integer>> rrsets,int size)
	{
		//ArrayList<ArrayList<Integer>> re_neighbor;
		
		for(int i=0;i<size;i++)
		{
			ArrayList<Integer> rrset=new ArrayList<Integer>();
			//long startTime = System.currentTimeMillis();
		    
			get_rrset(this.neighbor_reverse,rrset);
			//seed.add((int) Math.round(Math.random()*network.vertexNum));
			//network.spread(seed, 1);
			
			//long endTime = System.currentTimeMillis();
			//long searchTime = endTime - startTime;
			//System.out.println("time "+searchTime*0.001);
			if(rrset.size()>0)
			{
				rrsets.add(rrset);
				//System.out.println(rrset);
			}
			
			if(i % 100000 ==0)
			{
				//System.out.println(i);
			}
			
		}
		//System.out.println(size+ " rrsets generated.");
		
	}
	
	
	
	public void get_rrset(ArrayList<ArrayList<Integer>> re_neighbor,ArrayList<Integer> rrset)
	{
		int centerIndex = t;
		//long startTime = System.currentTimeMillis();
		//System.out.println("centerIndex "+centerIndex);
		switch(this.type)
		{
			case "IC":
				break;
			case "WC":
				break;
			case "LT":
				re_spreadOnceLT(re_neighbor,centerIndex,rrset);

				break;
			default:
				System.out.print("Invalid model");
		}
	}
	
	public void re_spreadOnceLT(ArrayList<ArrayList<Integer>> re_neighbor, int cindex, ArrayList<Integer> rrset)
	{
		rrset.add(cindex);
		while(true)
		{
			if(re_neighbor.get(cindex).size()==0)
			{
				rrset.clear();
				return;
			}
			cindex=re_neighbor.get(cindex).get((int) Math.floor(Math.random()*re_neighbor.get(cindex).size()));
			if(cfriends.contains(cindex))
			{
				return;
			}
			if(!rrset.contains((Integer)cindex))
			{
				rrset.add(cindex);
			}
			else
			{
				rrset.clear();
				return;
			}
			
		}
	}
	
	public void set_t_c(int index, ArrayList<Integer> list)
	{
		this.t=index;
		cfriends.clear();
		for(int i=0;i<list.size();i++)
		{
			cfriends.add(list.get(i));
		}
	}
	
	
	public double spreadOnce_invitation_set(ArrayList<Integer> invitation_set)
	{
		//System.out.println("spreadonce");

		for(int i=0;i<this.vertexNum;i++)
		{
			threshold[i]=Math.random();
			c_threshold[i]=0;
		}

		ArrayList<Boolean> state =new ArrayList<Boolean>();
		for(int i=0;i<this.vertexNum;i++)
		{
			state.add(false);
		}
		ArrayList<Integer> newActive =new ArrayList<Integer>();
		
		for(int j=0;j<cfriends.size();j++)
		{
			state.set(cfriends.get(j),true);	
			//if(intrinsic[seedSet.get(j)] >= price)
			newActive.add(cfriends.get(j));
		}

		while(newActive.size()>0)
		{
			if(newActive.contains(t))
			{
				return 1;
			}
			spreadOneRound__invitation_set(this.neighbor, newActive,state, invitation_set);
			
		}
		return 0;
	}
	
	public void spreadOneRound__invitation_set(ArrayList<ArrayList<Integer>> relationship, ArrayList<Integer> newActive, ArrayList<Boolean> state, ArrayList<Integer> invitation_set)
	{
		ArrayList<Integer> newActiveTemp=new ArrayList<Integer>();
		for(int i=0;i<newActive.size();i++)
		{
			
			int cseed=newActive.get(i);

			ArrayList<Integer> cseed_neighbor=relationship.get(cseed);
			for(int j=0;j<cseed_neighbor.size();j++)
			{
				//a++;
				int cseede=cseed_neighbor.get(j);
				if(!state.get(cseede) && invitation_set.contains(cseede))
				{
					c_threshold[cseede]=c_threshold[cseede]+1/(double) inDegree[cseede];
					if(c_threshold[cseede]>threshold[cseede])
					{  
							state.set(cseede, true);
							newActiveTemp.add(cseede);
					}
				}
			}
		}
		newActive.clear();
		for(int i=0;i<newActiveTemp.size();i++)
		{
			newActive.add(newActiveTemp.get(i));
		}
	}
	
}
