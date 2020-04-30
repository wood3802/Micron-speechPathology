#include <iostream> 
#include <fstream> 
#include <stdlib.h>  
#include <stdio.h>  
#include <string.h> 
#include <string> 
#include <vector>
#include <unistd.h>  
#include <dirent.h>  
using namespace std;

ofstream out;
ifstream in;


string file_csv = "info.csv";
char basePath[100];
char signalPatn[100];  
char tranPatn[100];
char pointPath[100];
char current[100];
  

vector<string> getFiles(string cate_dir)  
{  
    vector<string> files;// sotre file
    DIR *dir;  
    struct dirent *ptr;  
    char base[1000];  
   
    if ((dir=opendir(cate_dir.c_str())) == NULL)  
        {  
        perror("Open dir error...");  
                exit(1);  
        }  
   
    while ((ptr=readdir(dir)) != NULL)  
    {  
        if(strcmp(ptr->d_name,".")==0 || strcmp(ptr->d_name,"..")==0)    ///current dir OR parrent dir  
                continue;  
        int size = strlen(ptr->d_name);


        if (strcmp((ptr->d_name + ( size - 4)), ".txt")!=0)
            continue;
        
        files.push_back(ptr->d_name);    
    }  
    closedir(dir);  

 
//    sort(files.begin(), files.end());  
    return files;  
}  

// remove all space

  void trim(string &s)
  {
      /*
      if( !s.empty() )
      {
          s.erase(0,s.find_first_not_of(" "));
          s.erase(s.find_last_not_of(" ") + 1);
      }
      */
     int index = 0;
    if( !s.empty())
     {
         while( (index = s.find(' ',index)) != string::npos)
         {
             s.erase(index,1);
         }
     }
 
 }

void trim(string &s,const string &mark)
{
    unsigned int nSize = mark.size();
	while(1)
	{
		unsigned int pos = s.find(mark);
		if(pos == string::npos)
		{
			return;
		}
 
		s.erase(pos, nSize);

	}
}

string get_channel(string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(copy == 1){
			newstring.push_back(header_line[i]);
			start++;
		}
		if(header_line[i] == ':' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string get_sample( string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(copy == 1){
			newstring.push_back(header_line[i]);
			start++;
		}
		if(header_line[i] ==':' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string get_Precision( string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	int end = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(header_line[i] == '-' )
			end++;

		if(copy == 1  && end == 0){
			newstring.push_back(header_line[i]);
			start++;
		}

		if(header_line[i] ==':' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string get_duration_time( string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	int end = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(header_line[i] == '=' )
			end++;

		if(copy != 0  && end == 0){
			newstring.push_back(header_line[i]);
			start++;
		}
		
		if(header_line[i] ==':' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string get_duration_sample( string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	int end = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(header_line[i] == 's' )
			end++;

		if(copy != 0  && end == 0){
			newstring.push_back(header_line[i]);
			start++;
		}
		
		if(header_line[i] == '=' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string get_filesize( string header_line){
	trim(header_line);
	int start = 0;
	int copy = 0;
	int end = 0;
	string newstring;
	for(int i = 0; i < header_line.size(); i++){
		if(header_line[i] == 'k' )
			end++;

		if(copy != 0  && end == 0){
			newstring.push_back(header_line[i]);
			start++;
		}
		
		if(header_line[i] ==':' ){
			copy++;
		}
	}
	if ( !newstring.empty() )
		return newstring; 
	return "empty string";
}

string&   replace_all_distinct(string&   str,const   string&   old_value,const   string&   new_value)     
{     
    for(string::size_type   pos(0);   pos!=string::npos;   pos+=new_value.length())   {     
        if(   (pos=str.find(old_value,pos))!=string::npos   )     
            str.replace(pos,old_value.length(),new_value);     
        else   break;     
    }     
    return   str;     
}     





void video_csv( string filename ){
	string path = signalPatn;

	path.append("/");
	path.append(filename);
	in.open(path);
	out.open(file_csv,ios::app);
	string Records_ID;
	string root;
	string header_line;

	int i = 0;
	if (!in){
		cout<<"unknown file: "<<path<<endl;
		return;
	}
	else{
		while(getline(in,header_line)){
				switch (i){
				case 0:
					//time
					out<<"0000-00-00 00:00:00,";
					//record id
					Records_ID = current;
					int last_sl;
					for (int i = 0; i < Records_ID.size(); i++){
						if (Records_ID[i] == '/')
							last_sl = i;
					}
					Records_ID.erase(0,last_sl+1);
					out<<Records_ID<<",";
					// root
					if(filename[0] == 'f')
						out<<"sf0,";
					else
						out<<"sm0,";
					root = signalPatn;
					out<<signalPatn<<",";
					// purpose
					out<<"R"<<",";
					//noise file
					out<<"NULL"<<",";
					break;
				case 1:
					out<<filename<<",";
					break;
				case 2:
					out<<get_channel(header_line)<<",";
					break;
				case 3:
					out<<get_sample(header_line)<<",";
					break;
				case 4:
					out<<get_Precision(header_line)<<",";
					break;
				case 5:
					out<<get_duration_time(header_line)<<",";
					out<<get_duration_sample(header_line)<<",";
					break;
				case 6:
					out<<get_filesize(header_line)<<",";
					break;
				case 7:
					out<<get_filesize(header_line)<<",";
					break;
				default:
					break;
			}
			i++;
		}
	}
	in.close();

	//version
	out<<"SP.17_18,";
	//Trascript
	out<<"NULL,";

	/*
	 read what child said
	*/
	path = "";
	path = tranPatn;
	filename.erase(filename.size()-4,filename.size());
	string newf = filename;

	newf.append(".trn");
	path.append("/");
	path.append(newf);

	in.open(path);
	if (in){
		getline(in,header_line);
		//header_line.replace("\n", " ");
		replace_all_distinct(header_line, "," ," ");
		out<<header_line<<",";
	}
	else
	{
		out<<"no error,";
	}
	in.close();

	/*
		read error text
	*/

	path = "";
	path = pointPath;
	newf = filename;
	newf.append(".pnt");
	path.append("/");
	path.append(newf);
	in.open(path);
	if (in){
		getline(in,header_line);
		//header_line.replace(header_line.end(),1, "");
		replace_all_distinct(header_line, "," ," ");
		out<<header_line<<endl;
	}
	else{
		out<<"no error\n";
	}
	in.close();


	out.close();
}


void record_csv(){

}

int main(void)  
{  
    DIR *dir;  

    getcwd(current, sizeof(current));

    ///get the current absoulte path  
    memset(basePath, '\0', sizeof(basePath));  
    getcwd(basePath, 999);
    
    strcpy(signalPatn,basePath);
    strcat(signalPatn,"/signal");

    strcpy(pointPath,basePath);
    strcat(pointPath,"/point");

    strcpy(tranPatn,basePath);
    strcat(tranPatn,"/trans");

    printf("the current dir is : %s\n",basePath);  
  	printf("signal dir%s\n",signalPatn );
       
    cout<<endl<<endl;  
    vector<string> files=getFiles(signalPatn);  
    /*for (int i=0; i<files.size(); i++)  
    {  
        cout<<files[i]<<endl;  
    }  
  	*/

    for(int i = 0;  i<files.size(); i++){
    	video_csv(files[i]);
    }

    out.open("records.csv");
    string Records_ID;
	Records_ID = current;
	int last_sl;
	for (int i = 0; i < Records_ID.size(); i++){
		if (Records_ID[i] == '/')
			last_sl = i;
	}
	Records_ID.erase(0,last_sl+1);
	out<<Records_ID<<",";
    out<<"NULL,";
    out<<"NULL,";
    out<<"NULL,";
    out<<"-1,";
    out<<"r0"<<endl;

    out.close();
    return 0;  
}  
