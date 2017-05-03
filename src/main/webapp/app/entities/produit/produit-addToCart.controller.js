(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('ProduitAddToCartController', ProduitAddToCartController);

    ProduitAddToCartController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    function ProduitAddToCartController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        var vm = this;
        vm.ligneCommande = entity;  
        vm.ligneCommande.produit = Produit.get({id:$stateParams.id});
        vm.operations = Operation.query();
        vm.clear = clear; 
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
       
        function save () {
            vm.ligneCommande.quantite=1;
            vm.ligneCommande.etat= 'En attente';
            vm.leTarif = Produit.getOperationPrice({idOperation:vm.ligneCommande.operation.id, idProduit:vm.ligneCommande.produit.id});            
            vm.leTarif.$promise.then(function(data){
                vm.ligneCommande.tarif = data.montant;
                $scope.commandeForm.ligneCommandes.push(vm.ligneCommande);$uibModalInstance.close(true);
            });
        };
 }
})();
