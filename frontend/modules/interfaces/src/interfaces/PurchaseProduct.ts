export interface PurchaseProduct {
    id?: number;
    price: number;
    anchor_price: number;
    message: string | null;
    hours: number;
    weight: number;
    increment_balance_weight: number;
}
