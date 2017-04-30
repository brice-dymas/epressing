(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('ProduitAddToCartController', ProduitAddToCartController);

    ProduitAddToCartController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    function ProduitAddToCartController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        var vm = this;
        //vm.commandeForm = entity;

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
            console.log('Produit ligne commande = '+vm.ligneCommande.produit.libelle+" ayant pour ID: "+vm.ligneCommande.produit.id);
            console.log('Operation ligne commande = '+vm.ligneCommande.operation.libelle+" ayant pour ID: "+vm.ligneCommande.operation.id);
            vm.leTarif = Produit.getOperationPrice({idOperation:vm.ligneCommande.operation.id, idProduit:vm.ligneCommande.produit.id});            
            vm.leTarif.$promise.then(function(data){
                vm.ligneCommande.tarif = data.montant;
                $scope.commandeForm.ligneCommandes.push(vm.ligneCommande);
                console.log('ID of added element is '+vm.ligneCommande.id+" and its tarif is "+vm.ligneCommande.tarif);
                console.log('la taille de la liste de commande dans commandeForm est maintenant: '+$rootScope.commandeForm.ligneCommandes.length);
            });
        };         

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:produitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false; 
        }
 }
})();
