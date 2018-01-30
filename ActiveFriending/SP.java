package ActiveFriending;

import java.util.ArrayList;

public class SP {
	public static void run(ActiveFriendingInstance myinstance, ArrayList<Integer> result, ArrayList<Integer> appear_node_list, int size)
	{
		ArrayList<Integer> available_nodes=new ArrayList<Integer>();
		for(int i=0;i<appear_node_list.size();i++)
		{
			available_nodes.add(appear_node_list.get(i));
		}
		for(int i=0;i<myinstance.cfriends.size();i++)
		{
			available_nodes.add(myinstance.cfriends.get(i));
		}
		result.add(myinstance.t);
		while(true)
		{
			ArrayList<Integer> path=new ArrayList<Integer>();
			get_path(myinstance, myinstance.s, myinstance.t, path, available_nodes);
			//System.out.println(path.size());
			if(path.size()==0)
			{
				break;
			}
			else
			{
				boolean update=false;
				for(int i=0;i<path.size();i++)
				{
					available_nodes.remove((Integer) path.get(i));
					if(!result.contains(path.get(i)) && !myinstance.cfriends.contains(path.get(i)))
					{
						result.add(path.get(i));	
						update=true;
					}
					
					if(result.size()== size)
					{
						return;
					}
					
				}
				if(!update)
				{
					break;
				}
			}
		}
		if(result.size()<size)
		{
			for(int i=0;i<appear_node_list.size() && result.size()<size;i++)
			{
				if(!result.contains(appear_node_list.get(i)))
				{
					result.add(appear_node_list.get(i));
				}
				
			}
		}
	}
	
	private static void get_path(ActiveFriendingInstance myinstance, 
			int s, int t, 
			ArrayList<Integer> path, 
			ArrayList<Integer> available_nodes)
	{
		int[] pre =new int[myinstance.network.vertexNum];
		for(int i=0;i<myinstance.network.vertexNum;i++)
		{
			pre[i]=-1;
		}
		ArrayList<Integer> current_round=new ArrayList<Integer>();
		
		current_round.add(s);
		while(current_round.size()>0)
			
		{
			ArrayList<Integer> temp_current_round=new ArrayList<Integer>();
			for(int i=0;i<current_round.size();i++)
			{
				int seed_index=current_round.get(i);
				//int seed_index=appear_node_list.get(seed_index_in_appear_node_list);
				for(int j=0;j< myinstance.network.neighbor.get(seed_index).size();j++)
				{
					int seede_index=myinstance.network.neighbor.get(seed_index).get(j);
					if(seede_index==t)
					{
						pre[seede_index]=seed_index;
						path.add(seed_index);
						while(pre[seede_index] != s)
						{
							path.add(pre[seede_index]);
							seede_index=pre[seede_index];
						}
						return;
					}
					if(available_nodes.contains(seede_index) && pre[seede_index] <0)
					{
							pre[seede_index]=seed_index;
							temp_current_round.add(seede_index);
							
					}
				}
			}
			current_round.clear();
			for(int i=0; i<temp_current_round.size(); i++)
			{
				current_round.add(temp_current_round.get(i));
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
