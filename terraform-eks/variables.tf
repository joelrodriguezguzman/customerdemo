variable "eks_cluster_name" {
  description = "Name of the EKS cluster"
  type        = string
  default     = "customerEKSCluster"
}

variable "node_count" {
  description = "Number of nodes in the EKS cluster"
  type        = number
  default     = 2
}
variable "AWS_REGION" {
  description = "The AWS region to deploy resources in"
  type        = string
  default     = "us-east-1"  # or your preferred region
}
variable "AWS_ACCESS_KEY_ID" {
  description = "AWS Access Key ID"
  type        = string
}

variable "AWS_SECRET_ACCESS_KEY" {
  description = "AWS Secret Access Key"
  type        = string
}
