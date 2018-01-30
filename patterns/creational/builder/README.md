# Definition
The Builder pattern is a classic Gang of Four creational design pattern. This pattern, similar to the other creational patterns, such as factory method and abstract factory, is concerned with the creation of objects. The builder pattern allows you to enforce a step-by-step process to construct a complex object as a finished product. In this pattern, the step-by-step construction process remains same but the finished products can have different representations. For example in a house building, the step-by-step process includes the steps to create the foundation, structure, and roof followed by the steps to paint and furnish a house and these steps remain the same irrespective of the type of house to build. The finished product, which is a house, can have different representations. That is, it can be a concrete house, a prefabricated house, or a tree house.

# Definition properties
- Type: creation of the object
- Problem: to create a complex object
- Solution: to create the object step by step

# Participants in the Factory Method Pattern
- Product: a class that represents the product to create.
- Builder: is an interface to build the parts of a product.
- ConcreteBuilder: are concrete classes that implement Builder to construct and assemble parts of the product and return the finished product.
- Director: a class that directs a builder to perform the steps in the order that is required to build the product.
