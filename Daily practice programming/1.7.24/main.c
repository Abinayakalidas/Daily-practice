#include<stdio.h>
#include<string.h>
int longest(char str[],int n)
{
    int m=strlen(str);
    int f[26]={0};
    int d=0,max=0;
    for(int s=0,e=0;e<m;e++)
    {
        if(f[str[e] -'a']==0)
        {
            d++;
        }
        f[str[e]-'a']++;
        while(d>n)
        {
            f[str[s]-'a']--;
            if(f[str[s]-'a']==0)
            {
                d--;
            }
            s++;
        }
        max=(e-s+1>max)?(e-s+1):max;
    }
    return max;
}
int main()
{
    char str[100];
    int n;
    scanf("%s",str);
    scanf("%d",&n);
    int res=longest(str,n);
    printf("%d\n",res);
    return 0;
}
