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
        //vm.compteur = $rootScope.lignesCommandes.length;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
       

        function save () {
           // alert('operation ID '+vm.ligneCommande.produit.id);
            
            //vm.compteur = vm.compteur + 1;
            //vm.ligneCommande.id= vm.compteur;
            vm.ligneCommande.quantite=1;
            vm.ligneCommande.etat= 'En attente';            
            //vm.ligneCommande.produit = Produit.get({id:$stateParams.id});
            console.log('Produit ligne commande = '+vm.ligneCommande.produit.libelle+" ayant pour ID: "+vm.ligneCommande.produit.id);
            console.log('Operation ligne commande = '+vm.ligneCommande.operation.libelle+" ayant pour ID: "+vm.ligneCommande.operation.id);
            //vm.ligneCommande.tarif= Tarif.getOperationPrice({idOperation:vm.ligneCommande.operation.id, idproduit:vm.ligneCommande.produit.id});
            //console.log('ID ligne commande = '+vm.compteur);
            vm.ligneCommande.tarif= 1000;
            $scope.commandeForm.lignesCommandes.push(vm.ligneCommande);
            console.log('ID of added element is '+vm.ligneCommande.id+" and its tarif is "+vm.ligneCommande.tarif.montant);
            console.log('la taille de la liste de commande dans commandeForm est maintenant: '+$rootScope.commandeForm.lignesCommandes.length);
            onSaveSuccess;
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
