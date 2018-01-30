package ActiveFriending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActiveFriendingInstance {
	public ActiveFriendingNetwork network;
	public int s,t;
	public ArrayList<Integer> cfriends;
	//public ArrayList<Integer> invatition_set;
	public Integer[] nodes_by_outdegree; 
	public Integer[] nodes_by_indegree; 
	
	public ActiveFriendingInstance(String path, int vertexNum)
	{
		network=new ActiveFriendingNetwork(path, "LT" , vertexNum);
		cfriends=new ArrayList<Integer>();
		//invatition_set=new ArrayList<Integer>();
		nodes_by_outdegree=new Integer[network.vertexNum];
		nodes_by_indegree=new Integer[network.vertexNum];
		
		sort_nodes_by_outdegree();
		sort_nodes_by_indegree();
		set_s_t_shift(0,0);
		//set_s_t_index(0,0);
		//set_cfriends();
		//System.out.println(network.inDegree[t]);
		//network.set_t_c(t, cfriends);
		
	}
	
	
	public void get_rrsets(ArrayList<ArrayList<Integer>> rrsets, int size)
	{
		network.get_rrsets(rrsets, size);
	}
	
	public void get_rrsets_test(ArrayList<ArrayList<Integer>> rrsets)
	{
		//ArrayList<ArrayList<Integer>> re_neighbor;
		
		ArrayList<Integer> rrset_1=new ArrayList<Integer>();
		rrset_1.add(0);
		rrset_1.add(1);
		rrset_1.add(2);
		rrset_1.add(3);
		rrset_1.add(4);
		
		ArrayList<Integer> rrset_2=new ArrayList<Integer>();
		rrset_2.add(0);
		rrset_2.add(1);
		rrset_2.add(2);
		
		ArrayList<Integer> rrset_3=new ArrayList<Integer>();
		rrset_3.add(0);
		rrset_3.add(2);
		rrset_3.add(3);
		rrset_3.add(4);
		
		rrsets.add(rrset_1);
		rrsets.add(rrset_2);
		rrsets.add(rrset_3);
	}
	
	public double test_probability(ArrayList<Integer> invitation_set, int times)
	{
		double result=0;
		for(int i=0; i<times;i++)
		{
			result=result+network.spreadOnce_invitation_set(invitation_set);
			//System.out.println(i);
		}
		return result/times;
	}
	
	public double test_probability_rr(ArrayList<Integer> invitation_set, double p_max)
	{
		int rr_size= (int) (2*(Math.log(2*network.vertexNum))*(1+0.1)/(0.1*0.1*p_max*p_max));
		ArrayList<ArrayList<Integer>> rrsets=new ArrayList<ArrayList<Integer>>();
		boolean[] vector_invitation_set=new boolean[network.vertexNum];
		for(int i=0;i<network.vertexNum; i++)
		{
			vector_invitation_set[i]=false;
		}
		for(int i=0; i<invitation_set.size(); i++)
		{
			vector_invitation_set[invitation_set.get(i)]=true;
		}
		this.get_rrsets(rrsets, rr_size);
		double result=0;
		for(int i=0; i<rrsets.size(); i++)
		{
			boolean ifsubset=true;
			if(rrsets.get(i).size()==0)
			{
				ifsubset=false;
			}
			else
			{
				for(int j=0; j<rrsets.get(i).size(); j++)
				{
					if(!vector_invitation_set[rrsets.get(i).get(j)])
					{
						ifsubset=false;
						break;
					}
				}
			}
			if(ifsubset)
			{
				result++;
			}
			
		}
		return result/(double) rr_size;
	}
	
	public void set_s_t_shift(int s_shift, int t_shift)
	{
		this.s=nodes_by_outdegree[s_shift];
		cfriends.clear();
		set_cfriends();
		
		for(int i=0;i<network.vertexNum;i++)
		{
			
			if(!cfriends.contains(this.nodes_by_indegree[i+t_shift]))
			{
				t=this.nodes_by_indegree[i+t_shift];
				break;
			}
		}
		network.set_t_c(t, cfriends);
	}
	
	public void set_s_t_index(int s_index, int t_index)
	{
		this.s=s_index;
		cfriends.clear();
		set_cfriends();
		this.t=t_index;
		network.set_t_c(t, cfriends);
	}
	
	public void get_neighbors(int root, int round, ArrayList<Integer> neighbors, int limit)
	{
		ArrayList<Integer> reach_nodes=new ArrayList<Integer>();
		ArrayList<Integer> new_nodes=new ArrayList<Integer>();
		new_nodes.add(root);
		for(int i=0;i<round;i++)
		{
			ArrayList<Integer> tnew_nodes=new ArrayList<Integer>();
			if(i<round-1)
			{
				for(int j=0;j<new_nodes.size();j++)
				{
					//System.out.println(new_nodes.size()+" "+j);
					int t_index=new_nodes.get(j);
					for(int k=0;k<network.neighbor.get(t_index).size();k++)
					{
						int n_index=network.neighbor.get(t_index).get(k);
						if(!reach_nodes.contains(n_index))
						{
							reach_nodes.add(n_index);
							tnew_nodes.add(n_index);
						}
					}
				}
				new_nodes.clear();
				for(int j=0;j<tnew_nodes.size();j++)
				{
					new_nodes.add(tnew_nodes.get(j));
				}
				tnew_nodes.clear();
			}
			else
			{
				for(int j=0;j<new_nodes.size();j++)
				{
					int t_index=new_nodes.get(j);
					for(int k=0;k<network.neighbor.get(t_index).size();k++)
					{
						int n_index=network.neighbor.get(t_index).get(k);
						if(!reach_nodes.contains(n_index))
						{
							reach_nodes.add(n_index);
							neighbors.add(n_index);
							if(neighbors.size()>limit)
							{
								return;
							}
						}
					}
				}
			}
		}
		
		
	}
	
	private void set_cfriends()
	{
		
		cfriends.add(s);
		for(int i=0; i<network.neighbor.get(s).size();i++)
		{
			cfriends.add(network.neighbor.get(s).get(i));
		}
			
	}
	
	
	
	private void sort_nodes_by_outdegree()
	{
		
		
		HashMap<Integer,Integer> mymap=new HashMap<Integer,Integer>();
		for(int i=0;i<network.vertexNum;i++)
		{
			mymap.put(i, network.outDegree[i]);
		}
		//System.out.println(mymap.size());
		Map<Integer, Integer> sortedMap = sortByValue(mymap);
		
		sortedMap.keySet().toArray(nodes_by_outdegree);
	}
	
	private void sort_nodes_by_indegree()
	{
		
		
		HashMap<Integer,Integer> mymap=new HashMap<Integer,Integer>();
		for(int i=0;i<network.vertexNum;i++)
		{
			mymap.put(i, network.inDegree[i]);
		}
		//System.out.println(mymap.size());
		Map<Integer, Integer> sortedMap = sortByValue(mymap);
		
		sortedMap.keySet().toArray(nodes_by_indegree);
	}
	
	
	private static Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String wiki="data/wiki.txt";
		ActiveFriendingInstance myinstance=new ActiveFriendingInstance(wiki,8300);
		for(int i=0;i<100;i++)
		{
			
		}
		
		ArrayList<ArrayList<Integer>> rrsets=new ArrayList<ArrayList<Integer>> ();
		myinstance.get_rrsets(rrsets, 1000);
		for(int i=0;i<1000;i++)
		{
			String temp="";
			for(int j=0;j<rrsets.get(i).size();j++)
			{
				temp=temp+" "+rrsets.get(i).get(j);
			}
			System.out.println(temp);
		}
		
		
	}
}
