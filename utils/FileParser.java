package utils;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class FileParser {
    static public List<Point> readLife105File(File file){
        Scanner in;
        List<Point> positions = new ArrayList<>();
        // String[] splitStr;
        int x=0, y=0, ox=0, oy=0;

		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

        String line = in.nextLine();

        while(in.hasNextLine()){
            line = in.nextLine();
            if(line.charAt(0) == '#'){
                if(line.charAt(1) == 'P' || line.charAt(1) == 'p'){
                    String[] splitStr = line.trim().split("\\s+");

                    x = Integer.parseInt(splitStr[1]);
                    y = Integer.parseInt(splitStr[2]);
                    ox = x;
                    oy = y;
                }
            }else{
                if(line.length() > 0){
                    for(int i=0; i<line.length(); i++){
                        if(line.charAt(i) == '*'){
                            positions.add(new Point(x, y));
                        }
                        x += 1;
                    }
                    y += 1;
                    x = ox;
                }
            }
        }
        in.close();
        return positions;
    }

    static public int[][] readLifeRLEFile(File file){
        Scanner in;
        int x=0, y=0, ox=0, oy=0;

		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

        String line = in.nextLine();

        for(; line.charAt(0) == '#'; line = in.nextLine());

        Pattern pattern = Pattern.compile("^x = ([0-9]+), y = ([0-9]+), rule = (.+)$");
		Matcher matcher = pattern.matcher(line);
		
		if(!matcher.matches()) {
			return null;
		}

        int n = Integer.parseInt(matcher.group(1));
		int m = Integer.parseInt(matcher.group(2));
        String rule = matcher.group(3);
		int[][] positions = new int[m][n];
		int buffer = 0;

        do{
            line = in.nextLine();
			for(int j=0; j<line.length(); j++) {
				char c = line.charAt(j);
				if(Character.isDigit(c)) {
					buffer = 10*buffer + (int) (c - '0');
				}
				else if(c == 'o') {
					Arrays.fill(positions[y], x, Math.min(n, x+Math.max(1, buffer)), 1);
					x += Math.max(1, buffer);
					buffer = 0;
				}
				else if(c == 'b') {
					x += Math.max(1, buffer);
					buffer = 0;
				}
				else if(c == '$') {
					y += Math.max(1, buffer);
					x = 0;
					buffer = 0;
				}
				else if(c == '!')
					return positions;
				else
					return null;
			}
        }while(in.hasNext());

        return null;
    }
}
