(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('ProduitAddToCartController', ProduitAddToCartController);

    ProduitAddToCartController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande',  'usedTarif', '$localStorage'];

    function ProduitAddToCartController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, usedTarif, $localStorage)  {
        var vm = this;
        vm.ligneCommande = entity;  
        console.log(usedTarif);
        vm.tarifUtiliser = usedTarif;  
        vm.ligneCommande.produit = usedTarif.produit;
        vm.ligneCommande.operation = usedTarif.operation;
        vm.ligneCommande.tarif = usedTarif.montant;
        //vm.operations = Operation.query();
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
                console.log(vm.ligneCommande);
                $scope.commandeForm.ligneCommandes.push(vm.ligneCommande);
                $localStorage.commandeForm = $scope.commandeForm;
                $uibModalInstance.close(true);
        };
 }
})();
