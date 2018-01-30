package ActiveFriending;

import java.util.ArrayList;

public class RBA {
	
	public static double run(ActiveFriendingInstance myinstance, ArrayList<Integer> result, ArrayList<Integer> appear_node_list)
	{
		
		double epsilon_0=0.3;
		double epsilon_1=0.3;
		double epsilon_2=0.3;
		double epsilon_3=0.3;
		System.out.println(epsilon_0+" "+epsilon_1+" "+epsilon_2+" "+epsilon_3);
		double N=100;
		double p_max=get_pmax(myinstance, epsilon_0, N);
		System.out.println("p_max "+p_max);
		double l=get_l(epsilon_0,epsilon_1,epsilon_2,epsilon_3,N,p_max);
		System.out.println("l "+l);
		//l=Math.min(1000000, l);
		System.out.println("l "+l);
		ArrayList<ArrayList<Integer>> rrsets=new ArrayList<ArrayList<Integer>>();
		myinstance.get_rrsets(rrsets, (int) l);
		//myinstance.get_rrsets_test(rrsets);
		System.out.println("rrsets generated");
		System.out.println("l_1 "+rrsets.size());
		double eta=get_eta(rrsets,myinstance.network.vertexNum);
		double alpha=get_alpha(epsilon_1,epsilon_3,eta);
		double threshold=(alpha-epsilon_3)/(1+epsilon_1);
		get_invitationset(rrsets,myinstance,threshold, result, appear_node_list);
		return p_max;
	}
	
	private static double get_pmax(ActiveFriendingInstance myinstance, double epsilon, double N)
	{
		double gamma=1+(4*(Math.E-2)*(1+epsilon)*Math.log(N*2))/(epsilon*epsilon);
		System.out.println(gamma);
		double i=0;
		double j=0;
		while(j <= gamma)
		{
			i=i+1;
			ArrayList<ArrayList<Integer>>  rrsets=new ArrayList<ArrayList<Integer>>() ;
			myinstance.get_rrsets(rrsets, 1);
			if(rrsets.size()>0)
			{
				j=j+1;
			}
			
		}
		
		return gamma/i;
	}
	
	private static double get_l(double epsilon_0,double epsilon_1, double epsilon_2, double epsilon_3, double N, double p_max)
	{
		double delta_1=(2*(Math.log(N)+Math.log(2))*(2+epsilon_1)*(1+epsilon_0))/(epsilon_1*epsilon_1*p_max);
		double delta_2=((Math.log(N)+N*Math.log(2))*(2+epsilon_2)*(1+epsilon_0))/(epsilon_2*epsilon_2*p_max);
		double delta_3=(2*Math.log(N)*(1+epsilon_0))/(epsilon_3*epsilon_3*p_max);
		return Math.max(Math.max(delta_1, delta_2),delta_3);
	}
	
	private static double get_eta(ArrayList<ArrayList<Integer>> rrsets,int n)
	{
		int[] record=new int[n];
		for(int i=0;i<n;i++)
		{
			record[i]=0;
		}
		double c_max=Integer.MIN_VALUE;
		double maxsize=Integer.MIN_VALUE;
		double minsize=Integer.MAX_VALUE;
		for(int i=0;i<rrsets.size();i++)
		{
			if(rrsets.get(i).size()>maxsize)
			{
				maxsize=rrsets.get(i).size();
			}
			
			if(rrsets.get(i).size()<minsize)
			{
				minsize=rrsets.get(i).size();
			}
			for(int j=0; j<rrsets.get(i).size();j++)
			{
				int index=rrsets.get(i).get(j);
				record[index]++;
				if(record[index]>c_max)
				{
					c_max=record[index];
				}
			}
		}
		return c_max*maxsize/minsize;
	}
	
	private static double get_alpha(double epsilon_1, double epsilon_3, double eta)
	{
		System.out.println("alpha range "+(1-1/eta)+" "+1);
		return (2*eta-1)*(1+epsilon_1)/(2*eta)+epsilon_3;
	}
	
	private static void get_invitationset(ArrayList<ArrayList<Integer>> rrsets, 
			ActiveFriendingInstance myinstance,double threshold, 
			ArrayList<Integer> result,
			ArrayList<Integer> appear_node_list)
	{
		threshold=0.8;
		
		System.out.println("getting invitation set "+threshold);
		int vNum=myinstance.network.vertexNum;
		ArrayList<ArrayList<Integer>> appear_node_cover_list=new ArrayList<ArrayList<Integer>>();
		int[] current_appear_node_cover_size;
		
		int[] v_index_in_appear_list=new int[vNum];
		int[] c_requirement=new int[rrsets.size()];
		for(int i=0;i<vNum;i++)
		{
			v_index_in_appear_list[i]=-1;
		}
		
		for(int i=0; i<rrsets.size();i++)
		{
			int r_index=i;
			c_requirement[r_index]=rrsets.get(i).size();
			for(int j=0; j<rrsets.get(i).size(); j++)
			{
				int v_index=rrsets.get(i).get(j);
				if(v_index_in_appear_list[v_index]<0)
				{
					appear_node_list.add(v_index);
					ArrayList<Integer> temp=new ArrayList<Integer>();
					temp.add(r_index);
					appear_node_cover_list.add(temp);
					v_index_in_appear_list[v_index]=appear_node_cover_list.size()-1;
					 
				}
				else
				{
					appear_node_cover_list.get(v_index_in_appear_list[v_index]).add(r_index);
				}
			}
		}
		current_appear_node_cover_size=new int[appear_node_list.size()];
		
		for(int i=0; i<appear_node_list.size();i++)
		{
			current_appear_node_cover_size[i]=appear_node_cover_list.get(i).size();
		}
		System.out.println("appear_list.size "+appear_node_list.size());
		int c_cover=0;
		
		while(c_cover< rrsets.size()*threshold)
		{
			int temp_cover=Integer.MIN_VALUE;
			int c_index=0;
			int c_index_inappear=0;
			for(int i=0;i<appear_node_list.size();i++)
			{
				if(!result.contains(appear_node_list.get(i)) && current_appear_node_cover_size[i]>temp_cover)
				{
					temp_cover=current_appear_node_cover_size[i];
					c_index=appear_node_list.get(i);
					c_index_inappear=i;
				}
				
			}
			result.add(c_index);
			//if(result.size()==1000)
			//{
			//	return;
			//}
			//System.out.println("c_index "+c_index);
			//System.out.println("updating "+c_index);
			//System.out.println(v_cover.get(v_index_in_appear_list[c_index]));
			for(int i=0;i < appear_node_cover_list.get(c_index_inappear).size(); i++)
			{
				
				int r_index=appear_node_cover_list.get(c_index_inappear).get(i);
				//System.out.println(r_index);
				if(c_requirement[r_index]>0)
				{
					c_requirement[r_index]--;
					if(c_requirement[r_index]==0)
					{
						//System.out.println(r_index);
						c_cover++;
						for(int j=0;j<rrsets.get(r_index).size();j++)
						{
							int index_in_cover=v_index_in_appear_list[rrsets.get(r_index).get(j)];
							current_appear_node_cover_size[index_in_cover]--;
						}
					}
				}
				
				
			}
			//System.out.println(c_requirement[0]+" "+c_requirement[1]+" "+c_requirement[2]);
			//System.out.println("c_cover "+c_cover+" c_index "+c_index+" result.size "+result.size());

		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ActiveFriendingInstance myinstance=new ActiveFriendingInstance(wiki,8300);
		//{
			//myinstance.set_t(i);
		//	System.out.println("t "+myinstance.t+" "+myinstance.network.inDegree[myinstance.t]);
		//	ArrayList<Integer> result=new ArrayList<Integer>();
		//	ArrayList<Integer> appear_node_list=new ArrayList<Integer>();
		//	RBA.run(myinstance, result, appear_node_list);
		//	System.out.println("test_probability "+myinstance.test_probability(result, 10000));
			
			
		//}
		
		//ArrayList<ArrayList<Integer>> rrsets=new ArrayList<ArrayList<Integer>> ();
		
		
		
		
	}
}
