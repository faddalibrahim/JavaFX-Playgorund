# JavaFx Notes

Stage === JFrame  
Scene === JPanel : drawing surface for graphical content and a container for nodes

RootNodes === Layout Managers

```java

//basic setup
Group root = new Group();
Scene scene = new Scene(root,300,250,color.BLACK);
stage.setScence(scene);
stage.show();
```

```java
Text text = new Text("This is a text")
text.setText("This is a text")
text.setX(50)
text.setY(50)
text.setFont(Font.font("Verdana",50));
text.setFill(Color.LIMEGREEN)

//adding to root node
root.getChildren().add(text)
```

```java
Line line = new Line();
line.setStartX(200);
line.setStartY(400);

line.setEndX(200);
line.setEndY(200);
line.setStroke(Color.RED)
line.setStrokeWidth(5);
line.setOpacity(0.5);
line.setRotate(45)
```

```java
Rectangle rectangle = new Rectangle();
rectangle.setX(200);
rectangle.setY(400);

rectangle.setWidth(200);
rectangle.setHeight(200);

rectangle.setFill(Color.BLUE )
```
