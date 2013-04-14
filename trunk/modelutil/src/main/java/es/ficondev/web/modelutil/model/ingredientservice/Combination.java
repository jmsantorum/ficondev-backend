package es.ficondev.web.modelutil.model.ingredientservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.ficondev.web.modelutil.model.plate.Plate;

public class Combination {

	static List<Plate> c;
	List<Plate> d = new ArrayList<Plate>();

	public Combination(List<Plate> lis){
		c = new ArrayList<Plate>();
		withoutPermutation(lis);
		d = lis;
	}

	public List<List<Plate>> Ar() {
		List<List<Plate>> res = new ArrayList<List<Plate>>();
		Iterator<Plate> iter = c.iterator();

		int tam = d.size();
		int x = 1;
		double a[] = new double[tam];

		for(int m=1; m<=tam; m++){
			double n = 1;
			double r = 1;
			double aux1 = 1;
			for(int i=1; i<=tam; n*=i,i++);
			int aux = (tam-m);
			for(int i=1; i<=aux; aux1*=i,i++);
			for(int i=1; i<=m; r*=i,i++);
			a[m-1] = n/(aux1*r);
		}

		while(iter.hasNext()) {
			for(int i=0; i<a.length; i++) {
				for(int j=1; j<=a[i]; j++) {
					List<Plate> cad = new ArrayList<Plate>();
					for(int k=1; k<=x; k++) {
						cad.add(iter.next());
					}
					res.add(cad);
				}
				x++;
			}
		}
		
		return res;
	}

	public static void withoutPermutation(List<Plate> list) {
		Object[] o = list.toArray();
		for (int m = 1; m <= list.size(); m++) {
			int[] posArr = new int[m];
			posArr[0] = 0;
			if (m > 1) {
				for (int i = 1; i < m; i++) {
					posArr[i] = i;
				}
			}
			combine(posArr, m - 1, m, o);
		}
	}

	public static void combine(int[] posArr, int posCam, int dea, Object[] o) {
		int quantity = o.length;
		int j;
		for (j = 0; j < posArr.length; j++) {
			c.add((Plate) o[posArr[j]]);
		}
		posArr[posCam]++;
		if (posArr[posCam] < quantity) {
			combine(posArr, posCam, dea, o);
		}
		else {
			int newPosCam = posCam - 1;
			if (newPosCam >= 0) {
				posArr[newPosCam]++;
				posArr[posCam] = posArr[posCam - 1] + 1;
				if (posArr[newPosCam] < quantity - 1) {
					combine(posArr, posCam, dea, o);
				}
				else {
					boolean exit = false;
					if (newPosCam != 0) {
						while (posArr[newPosCam] >= quantity - 1 || (exit && newPosCam > 0)) {
							newPosCam--;
							posArr[newPosCam]++;
							for (int i = newPosCam + 1; i < dea; i++) {
								posArr[i] = posArr[i - 1] + 1;
								exit = posArr[i] == quantity;
							}
						}
						if (!exit) {
							combine(posArr, posCam, dea, o);
						}
					}
				}
			}
		}
	}

}