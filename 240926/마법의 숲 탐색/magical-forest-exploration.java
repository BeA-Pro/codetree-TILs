import java.io.*;
import java.util.*;

public class Main {
    static int R,C,K;
    static int[][] map;
    static int cury, curx;
    static int[] dy = {-1,1,0,0}, dx = {0,0,-1,1};
    // 북 동 남 서

    private static class pos{
        int y, x;
        pos(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    static void clean(int y, int x){
        map[y+1][x]= map[y-1][x]= map[y][x+1]= map[y][x-1]= map[y][x] = 0;
    }
    static boolean check(int y, int x){
        if(y+1 == R + 2 || x - 1 < 0 || x + 1 == C) return false; // 범위 밖으로 나가는 경우
        if(map[y+1][x]!=0 || map[y-1][x]!=0 || map[y][x+1]!=0 || map[y][x-1]!=0 || map[y][x]!=0) return false; // 다른 블록이 있는 경우
        return true;
    }

    static void move(int y,int x,int dir, int num){
        map[y+1][x]= map[y-1][x]= map[y][x+1]= map[y][x-1]= map[y][x] = num;
        if(dir == 0) map[y-1][x] = -num;
        else if(dir == 1) map[y][x+1] = -num;
        else if(dir == 2) map[y+1][x] = -num;
        else map[y][x-1] = -num;
    }

    public static boolean down(int ci, int dir, int num){
        int y = 1, x = ci;
        if(!check(y,x)) return false;
        move(y,x,dir,num);
        // System.out.println("check");
        // for(int i=0;i<R+2;i++){
        //     for(int j=0;j<C;j++){
        //         System.out.printf("%d ",map[i][j]);
        //     }
        //     System.out.println();
        // }
        while(true){
            // System.out.println(y + " "+x);
            clean(y,x);
            if(check(y+1,x)){
                move(y+1,x,dir,num);
                y++;
            }
            else if(check(y,x-1) && check(y+1,x-1)){
                move(y+1,x-1,(dir+3)%4,num);
                y++;
                x--;
                dir = (dir+3)%4;
            }
            else if(check(y,x+1) && check(y+1,x+1)){
                move(y+1,x+1,(dir+1)%4,num);
                y++;
                x++;
                dir = (dir+1)%4;
            }else{
                move(y,x,dir,num);
                break;
            }
            // System.out.println("check2");
            // for(int i=0;i<R+2;i++){
            //     for(int j=0;j<C;j++){
            //         System.out.printf("%d ",map[i][j]);
            //     }
            // System.out.println();
            // }
        }

        curx = x;
        cury = y;
        if(y<=1) return false;
        return true;
    }

    static int center_move(){
        Queue<pos> q = new LinkedList<>();
        q.add(new pos(cury,curx));
        System.out.println(cury+" "+curx);
        boolean[][] visit = new boolean [R+2][C];
        visit[cury][curx] = true;
        int max = cury;
        
        while(!q.isEmpty()){
            pos cur = q.poll();
            int num = map[cur.y][cur.x];
            max = Math.max(cur.y,max);
            for(int i=0;i<4;i++){
                int ny = dy[i]+cur.y, nx = dx[i]+cur.x;
                if(!(0<=ny && ny<R+2 && 0<=nx && nx<C)) continue;
                if(map[ny][nx] == 0 || visit[ny][nx]) continue; 
                if(num < 0 || map[ny][nx] == num || map[ny][nx] == - num){
                    q.add(new pos(ny,nx));
                    visit[ny][nx] = true;
                }
            }
        }
        System.out.println(max);
        return max;
    }

    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().trim().split(" ");
        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);
        map = new int[R+2][C];
        int answer = 0;
        for(int num=1;num<=K;num++){
            input = br.readLine().trim().split(" ");
            int ci = Integer.parseInt(input[0]) - 1;
            int di = Integer.parseInt(input[1]);
            if(!down(ci,di,num)){
                map = new int[R+2][C];
                down(ci,di,num);
            }
            System.out.println("check2");
            for(int i=0;i<R+2;i++){
                for(int j=0;j<C;j++){
                    System.out.printf("%d ",map[i][j]);
                }
            System.out.println();
            }
            answer += center_move() - 1;
        }
        System.out.println(answer);
    }
}