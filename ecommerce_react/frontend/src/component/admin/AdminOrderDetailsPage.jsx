import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import '../../style/adminOrderDetails.css';
import ApiService from "../../service/ApiService";

const OrderStatus = ["PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED", "RETURNED"];

const AdminOrderDetailsPage = () => {
    const { itemId } = useParams();
    const [orderItems, setOrderItems] = useState([]);
    const [message, setMessage] = useState('');
    const [selectedStatus, setSelectedStatus] = useState({});

    useEffect(() => {
        fetchOrderDetails(itemId);
    }, [itemId]);

    const fetchOrderDetails = async (itemId) => {
        try {
            const response = await ApiService.getOrderItemById(itemId);
            setOrderItems(response.orderItemList || []);
        } catch (error) {
            console.error(error.message || error);
        }
    };

    const handleStatusChange = (orderItemId, newStatus) => {
        setSelectedStatus({ ...selectedStatus, [orderItemId]: newStatus });
    };

    const handleSubmitStatusChange = async (orderItemId) => {
        try {
            await ApiService.updateOrderitemStatus(orderItemId, selectedStatus[orderItemId]);
            setMessage('Order item status was successfully updated');
            setTimeout(() => {
                setMessage('');
            }, 3000);
        } catch (error) {
            setMessage(error.response?.data?.message || error.message || 'Unable to update order item status');
        }
    };

    return (
        <div className="order-details-page">
            {message && <div className="message">{message}</div>}
            <h2>Order Details</h2>
            {orderItems.length ? (
                orderItems.map((orderItem) => (
                    <div key={orderItem.id} className="order-item-details">
                        <div className="info">
                            <h3>Order Information</h3>
                            <p><strong>Order Item ID:</strong> {orderItem.id}</p>
                            <p><strong>Quantity:</strong> {orderItem.quantity}</p>
                            <p><strong>Total Price:</strong> {orderItem.price}</p>
                            <p><strong>Order Status:</strong> {orderItem.status}</p>
                            <p><strong>Date Ordered:</strong> {new Date(orderItem.createdAt).toLocaleDateString()}</p>
                        </div>
                        <div className="info">
                            <h3>User Information</h3>
                            <p><strong>Name:</strong> {orderItem?.user?.name || 'Unknown'}</p>
                            <p><strong>Email:</strong> {orderItem?.user?.email || 'Unknown'}</p>
                            <p><strong>Phone:</strong> {orderItem?.user?.phoneNumber || 'Unknown'}</p>
                            <p><strong>Role:</strong> {orderItem?.user?.role || 'Unknown'}</p>

                            <div className="info">
                                <h3>Delivery Address</h3>
                                <p><strong>Country:</strong> {orderItem?.user?.address?.country || 'N/A'}</p>
                                <p><strong>State:</strong> {orderItem?.user?.address?.state || 'N/A'}</p>
                                <p><strong>City:</strong> {orderItem?.user?.address?.city || 'N/A'}</p>
                                <p><strong>Street:</strong> {orderItem?.user?.address?.street || 'N/A'}</p>
                                <p><strong>Zip Code:</strong> {orderItem?.user?.address?.zipcode || 'N/A'}</p>
                            </div>
                        </div>
                        <div>
                            <h2>Product Information</h2>
                            <img
                                src={orderItem?.product?.imageUrl || '/default-image.png'}
                                alt={orderItem?.product?.name || 'No product name'}
                            />
                            <p><strong>Name:</strong> {orderItem?.product?.name || 'N/A'}</p>
                            <p><strong>Description:</strong> {orderItem?.product?.description || 'N/A'}</p>
                            <p><strong>Price:</strong> {orderItem?.product?.price || 'N/A'}</p>
                        </div>
                        <div className="status-change">
                            <h4>Change Status</h4>
                            <select
                                className="status-option"
                                value={selectedStatus[orderItem.id] || orderItem.status}
                                onChange={(e) => handleStatusChange(orderItem.id, e.target.value)}
                            >
                                {OrderStatus.map((status) => (
                                    <option key={status} value={status}>{status}</option>
                                ))}
                            </select>
                            <button
                                className="update-status-button"
                                onClick={() => handleSubmitStatusChange(orderItem.id)}
                            >
                                Update Status
                            </button>
                        </div>
                    </div>
                ))
            ) : (
                <p>Loading order details...</p>
            )}
        </div>
    );
};

export default AdminOrderDetailsPage;
