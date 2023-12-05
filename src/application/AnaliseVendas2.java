package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Sale;

public class AnaliseVendas2 {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){

			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();	
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), 
						Integer.parseInt(fields[1]), 
						fields[2], 
						Integer.parseInt(fields[3]), 
						Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			HashMap<String, Double> listMap = new HashMap<>();
			
			for (Sale sale : list) {
				listMap.put(sale.getSeller(), 0.0);
			}
			
			for(String seller : listMap.keySet()) {
				double totalPerSeller = list.stream()
						.filter(t -> t.getSeller().equals(seller))
						.map(t -> t.getTotal())
						.reduce(0.0, (x, y) -> x + y);
				listMap.put(seller, totalPerSeller);
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			for(String seller : listMap.keySet()) {
				System.out.printf(seller + " - %.2f%n", listMap.get(seller));
			}
			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		sc.close();
	}
}
