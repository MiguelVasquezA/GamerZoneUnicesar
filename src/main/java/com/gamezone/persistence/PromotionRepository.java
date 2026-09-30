package com.gamezone.persistence;

import com.gamezone.model.Promotion;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.BulkPurchaseDiscount;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing promotions persistence in a CSV file.
 */
public class PromotionRepository {
    private static final String FILE_PATH = "data/promotions.csv";

    /**
     * Loads all promotions from the CSV file.
     *
     * @return A list of promotions, or an empty list if the file does not exist.
     */
    public List<Promotion> loadAll() {
        List<Promotion> promotions = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return promotions;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;

                String type = data[0].trim();
                String id = data[1].trim();
                String name = data[2].trim();
                LocalDate startDate = LocalDate.parse(data[3].trim());
                LocalDate endDate = LocalDate.parse(data[4].trim());

                switch (type) {
                    case "PERCENTAGE":
                        double percentage = Double.parseDouble(data[5].trim());
                        promotions.add(new PercentageDiscount(id, name, startDate, endDate, percentage));
                        break;
                    case "CATEGORY":
                        double catPercentage = Double.parseDouble(data[5].trim());
                        String category = data[6].trim();
                        promotions.add(new CategoryDiscount(id, name, startDate, endDate, catPercentage, category));
                        break;
                    case "BULK":
                        double bulkPercentage = Double.parseDouble(data[5].trim());
                        int minQuantity = Integer.parseInt(data[7].trim());
                        promotions.add(new BulkPurchaseDiscount(id, name, startDate, endDate, bulkPercentage, minQuantity));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return promotions;
    }

    /**
     * Saves all promotions to the CSV file.
     *
     * @param promotions The list of promotions to save.
     */
    public void saveAll(List<Promotion> promotions) {
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("TYPE,ID,NAME,START_DATE,END_DATE,PERCENTAGE,TARGET_CATEGORY,MIN_QUANTITY");
            for (Promotion p : promotions) {
                if (p instanceof PercentageDiscount) {
                    PercentageDiscount pd = (PercentageDiscount) p;
                    pw.printf("PERCENTAGE,%s,%s,%s,%s,%.2f,,\n",
                            pd.getId(), pd.getName(), pd.getStartDate(), pd.getEndDate(), pd.getPercentage());
                } else if (p instanceof CategoryDiscount) {
                    CategoryDiscount cd = (CategoryDiscount) p;
                    pw.printf("CATEGORY,%s,%s,%s,%s,%.2f,%s,\n",
                            cd.getId(), cd.getName(), cd.getStartDate(), cd.getEndDate(), cd.getPercentage(), cd.getTargetCategory());
                } else if (p instanceof BulkPurchaseDiscount) {
                    BulkPurchaseDiscount bd = (BulkPurchaseDiscount) p;
                    pw.printf("BULK,%s,%s,%s,%s,%.2f,,%d\n",
                            bd.getId(), bd.getName(), bd.getStartDate(), bd.getEndDate(), bd.getPercentage(), bd.getMinQuantity());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
