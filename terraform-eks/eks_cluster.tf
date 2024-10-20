data "aws_iam_role" "existing_eks_cluster_role" {
  name = "eks_cluster_role"
}
resource "aws_iam_role" "eks_cluster_role" {
  count = length(data.aws_iam_role.existing_eks_cluster_role.arn) == 0 ? 1 : 0
  
  name = "eks_cluster_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "eks.amazonaws.com"
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

resource "aws_iam_role_policy_attachment" "eks_cluster_policy" {
  count      = length(aws_iam_role.eks_cluster_role)
  role       = aws_iam_role.eks_cluster_role[0].name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

resource "aws_eks_cluster" "eks" {
  count     = length(aws_iam_role.eks_cluster_role)
  name     = "customerdemo"
  role_arn = aws_iam_role.eks_cluster_role[0].arn

  vpc_config {
    subnet_ids = [aws_subnet.eks_subnet_1[0].id, aws_subnet.eks_subnet_2[0].id]
  }
}
