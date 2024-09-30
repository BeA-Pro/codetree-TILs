import java.util.*;
import java.io.*;


public class Main {
    static class pos{
        int y,x;
        pos(int y,int x){
            this.y = y;
            this.x = x;
        }
    }
    static int[][] info = new int[5][5];
    static int K, M;
    static LinkedList<Integer> wall = new LinkedList<>();
    static int answer = -1, ans_y = -1, ans_x = -1, ans_a = -1;
    static int[] dy = {-1,1,0,0}, dx = {0,0,-1,1};

    static int delBlock(int[][] a){
        boolean[][] visit = new boolean [5][5];
        int total = 0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                // System.out.println(a[i][j]+" "+visit[i][j]);
                if(!visit[i][j]){
                    // System.out.println(i+" "+j+" visit");
                    visit[i][j] = true;
                    int num = a[i][j];
                    Queue<pos> q = new LinkedList<>();
                    Queue<pos> save = new LinkedList<>();
                    q.add(new pos(i,j));
                    int cnt = 0;
                    while(!q.isEmpty()){
                        pos cur = q.poll();
                        save.add(new pos(cur.y,cur.x));
                        // a[cur.y][cur.x] = 0;
                        cnt++;
                        for(int k=0;k<4;k++){
                            int ny = cur.y + dy[k], nx = cur.x + dx[k];
                            if(0<= ny && ny < 5 && 0<= nx && nx < 5 && !visit[ny][nx] && num == a[ny][nx]){
                                visit[ny][nx] = true;
                                q.add(new pos(ny,nx));
                            }
                        }
                    }
                    // System.out.println(cnt);
                    if(cnt >=3){
                        total+=cnt;
                        while(!save.isEmpty()){
                            pos cur = save.poll();
                            a[cur.y][cur.x] = 0;
                        }
                    }
                }
            }
        }
        
        return total;
    }

    static void addBlock(int[][] a){
        for(int j=0;j<5;j++){
            for(int i = 4;i>=0;i--){
                if(a[i][j]==0){
                    // if(wall.isEmpty()) continue;
                    a[i][j] = wall.removeFirst();
                }
            }
        }
    }

    static void copyMap(int[][] a){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                a[i][j] = info[i][j];
            }
        }
    }

    static void rotate(int ceny, int cenx, int angle, int[][] cop){
        int temp_ans = -1, temp_y = -1, temp_x = -1;
        LinkedList<Integer> temp = new LinkedList<>();
        for(int i=0;i<2;i++) temp.add(cop[ceny-1][cenx-1+i]);
        for(int i=0;i<2;i++) temp.add(cop[ceny-1+i][cenx+1]);
        for(int i=0;i<2;i++) temp.add(cop[ceny+1][cenx+1-i]);
        for(int i=0;i<2;i++) temp.add(cop[ceny+1-i][cenx-1]);

        if(angle==0) for(int i=0;i<2;i++) temp.addFirst(temp.removeLast());
        else if(angle==1) for(int i=0;i<4;i++) temp.addFirst(temp.removeLast());
        else for(int i=0;i<6;i++) temp.addFirst(temp.removeLast());

        for(int i=0;i<2;i++) cop[ceny-1][cenx-1+i] = temp.removeFirst();
        for(int i=0;i<2;i++) cop[ceny-1+i][cenx+1] = temp.removeFirst();
        for(int i=0;i<2;i++) cop[ceny+1][cenx+1-i] = temp.removeFirst();
        for(int i=0;i<2;i++) cop[ceny+1-i][cenx-1] = temp.removeFirst();

    }

    // static void getScore(int[][] cop, LinkedList<Integer> temp_wall,int y, int x,int angle){
    //     int temp_ans = -1, temp_y = -1, temp_x = -1;
    //     int cnt = 0;
    //     boolean visit = new boolean[7][7];
    //     for(int i=0;i<7;i++){
    //         for(int j=0;j<)
    //     }
    // }

    static void printInfo(int[][] a){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.printf("%d ",a[i][j]);
            }
            System.out.println();
        }
    }

    static void cal(){

        int[][] cop = new int[5][5];
        for(int i=1;i<4;i++){
            for(int j=1;j<4;j++){
                for(int angle = 0; angle < 3; angle ++){
                    copyMap(cop);
                    // printInfo(cop);
                    rotate(i,j,angle,cop);
                    // printInfo(cop);
                    int score = delBlock(cop);
                    // printInfo(cop);
                    // System.out.println(i+" "+j+" "+angle);
                    
                    // int score = 0;
                    if(score == 0) continue;
                    if(answer == -1 || score > answer){
                        answer = score;
                        ans_y = i;
                        ans_x = j;
                        ans_a = angle;
                    }else if(score == answer){
                        boolean flag = false;
                        if(angle < ans_a) flag = true;
                        else if(ans_a == angle && j < ans_x) flag = true;
                        else if(ans_a == angle && j == ans_x && i < ans_y) flag = true;
                        
                        if(flag){
                            answer = score;
                            ans_y = i;
                            ans_x = j;
                            ans_a = angle;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        K = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        for(int i=0;i<5;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<5;j++){
                info[i][j] = Integer.parseInt(input[j]);
            }
        }

        input = br.readLine().trim().split(" ");
        for(int i=0;i<M;i++) wall.add(Integer.parseInt(input[i]));

        for(int turn = 0; turn < K; turn++){
            answer = ans_y = ans_x = ans_a = -1;
            cal();
            // LinkedList temp_wall = new LinkedList<>(wall);
            if(answer == -1) System.out.printf(" ");
            else{
                
                //System.out.println(answer+" "+ans_y+" "+ans_x+" "+ans_a);
                rotate(ans_y,ans_x,ans_a,info);
                //printInfo(info);
                int score = 0;
                answer = 0;
                while(true){
                    score = delBlock(info);
                    //System.out.println("after del");
                    //printInfo(info);
                    if(score == 0) break;
                    answer+=score;
                    addBlock(info); 
                    //System.out.println("after add");
                    //printInfo(info);
                }
                System.out.printf("%d ",answer);
            }
        }

    }
}