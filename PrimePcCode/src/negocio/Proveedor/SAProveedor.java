/**
 * 
 */
package negocio.Proveedor;

import java.util.Set;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public interface SAProveedor {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param proveedor
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int altaProveedor(TProveedor proveedor);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idProducto
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Set<TProveedor> leerProveedorPorProducto(Integer idProducto);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idProducto
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int vincularProductoProveedor(TProveedorProducto idProducto);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param tProveedorProducto
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int desvincularProductoProveedor(TProveedorProducto tProveedorProducto);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idProveedor
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int bajaProveedor(Integer idProveedor);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param tProveedor
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int modificarProveedor(TProveedor tProveedor);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param id
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public TProveedor leerProveedor(Integer id);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Set<TProveedor> leerTodosProveedores();
}