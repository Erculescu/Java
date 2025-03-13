class GenericPackage {
    //I. Define all the necessary fields here
    private final String uniqueID;
    private double weight;
    private final String packageName;
    private static String courierName;
    //II. Define all the necessary constructors and methods here


    public GenericPackage(String uniqueID, String packageName, double weight,String courierName) {
        this.uniqueID = uniqueID;
        this.packageName = packageName;
        this.weight = weight;
        if(GenericPackage.courierName==null){
            GenericPackage.courierName=courierName;
        }
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPackageName() {
        return packageName;
    }

    public static String getCourierName() {
        return courierName;
    }

    public static void setCourierName(String courierName) {
        GenericPackage.courierName = courierName;
    }

    @Override
    public String toString() {
        return "GenericPackage{" +
                "uniqueID='" + uniqueID + '\'' +
                ", weight=" + weight +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    public void addItem(double itemWeight) {
        //III.a. Enter your code here
        //You can also define any additional methods
        if(itemWeight>0)
        {this.weight+=itemWeight;}
        else{
            throw new IllegalArgumentException("Greutatea pachetului trebuie sa fie pozitiva!");
        }
    }

    public boolean checkID() {
        //III.b. Enter your code here
        //You can also define any additional methods

        //Remove the line below after you implement the method!
        if(!uniqueID.matches("\\d+")){
            return false;
        }
        int nrimp=0;
        int sum=0;
        for(char c:uniqueID.toCharArray()){
            int cifra=c-'0';
            sum=sum+cifra;
            if(cifra%2==1){
                nrimp++;
            }
        }
        if(nrimp%3!=0){
            return false;
        }
        if(sum%4!=0){
            return false;
        }
        for(int i=0;i<uniqueID.length()-1;i++){
            int cif1=uniqueID.charAt(i)-'0';
            int cif2=uniqueID.charAt(i+1)-'0';
            if(Math.abs(cif1-cif2)>=5){
                return false;
            }
        }
        if(uniqueID.length()<10||uniqueID.length()>12){
            return false;
        }
    return true;
    }

    public String computeDetails() {
        //III.c. Enter your code here
        //You can also define any additional methods

        //Remove the line below after you implement the method!
        return "Pachetul" +packageName+ " avand codul " +uniqueID+ " si greutatea "+weight+ " apartine curierului "+courierName+".\n";
    }


    /*-----------------------------------------------------------
     * The methods below are used for Testing Purposes only.
     * Do not modify them.
     -----------------------------------------------------------*/
    protected void printObjectFields() {
        System.out.println("[TEST] Pachetul " + this.packageName + " avand codul "
                + this.uniqueID + " si greutatea " + this.weight
                + " apartine curierului " + GenericPackage.courierName + ".");
    }

}