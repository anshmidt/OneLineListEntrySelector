# OneLineListEntrySelector
Android custom view which provides simple way to select list entries

## How does it look

<img src="https://user-images.githubusercontent.com/12444628/47957595-0bd9a180-dfca-11e8-8c7b-f2f690395483.png" alt="Screen 1" width="360" height="720"/>

<img src="https://user-images.githubusercontent.com/12444628/47957642-e731f980-dfca-11e8-9d7c-7b303abc828f.gif" alt="Screen 1" width="360" height="720"/>

## Usage
For working implementation of this library check app directory of this project.
   
```java
OneLineListEntrySelector sizeSelector = findViewById(R.id.size_list_entry_selector);
ArrayList<String> sizesList = new ArrayList<>();
sizesList.add("Small");
sizesList.add("Medium");
sizesList.add("Large");
sizeSelector.setList(sizesList);
sizeSelector.setInitialEntryNumber(1);
sizeSelector.setOnValueChangeListener(new OneLineListEntrySelector.OnValueChangeListener() {
    @Override
    public void onValueChange(OneLineListEntrySelector view, String oldValue, String newValue) {
        Toast.makeText(MainActivity.this, "New value is " + newValue, Toast.LENGTH_SHORT).show();
    }
});
```
    
## Attributes

`backgroundColor`

`textColor`

`buttonColor` (If not specified, then color of the buttons will be the same as textColor)

`textSize`

`buttonSize`

`buttonPadding`

`dynamicWidthEnabled` (If false, the width of the textview doesn't change when different entries are selected, 
it always equals to the length of the longest entry. If true, the width of the textview equals to the length of 
the current entry, i.e. it's dynamic)


## Methods

`setInitialEntryNumber(int initialEntryNumber)`

`setList(ArrayList list)`

`setEntry(int newEntryNumber)`

`getEntry()`

`setOnValueChangeListener(OnValueChangeListener onValueChangeListener)`



## minSdkVersion
minSdkVersion = API 21



