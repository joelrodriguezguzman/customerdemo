data "aws_iam_role" "existing_eks_node_role" {
  name = "eks_node_role"
}
resource "aws_iam_role" "eks_node_role" {
  count = length(data.aws_iam_role.existing_eks_node_role.arn) == 0 ? 1 : 0

  name = "eks_node_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
    }]
  })

  lifecycle {
    create_before_destroy = true
    ignore_changes = [
      name
    ]
  }

}

resource "aws_iam_role_policy_attachment" "eks_worker_node_policy" {
  count      = length(aws_iam_role.eks_node_role)
  role       = aws_iam_role.eks_node_role[0].name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}

resource "aws_iam_role_policy_attachment" "eks_cni_policy" {
  count      = length(aws_iam_role.eks_node_role)
  role       = aws_iam_role.eks_node_role[0].name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
}

resource "aws_eks_node_group" "node_group" {
  count      = length(aws_iam_role.eks_node_role)
  cluster_name    = aws_eks_cluster.eks[0].name
  node_group_name = "standard-workers"
  node_role_arn   = aws_iam_role.eks_node_role[0].arn
  subnet_ids = [aws_subnet.eks_subnet_1[0].id, aws_subnet.eks_subnet_2[0].id]

  scaling_config {
    desired_size = 3
    max_size     = 3
    min_size     = 2
  }

  instance_types = ["t3.medium"]
}
