(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DomicileDialogController', DomicileDialogController);

    DomicileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Domicile'];

    function DomicileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Domicile) {
        var vm = this;

        vm.domicile = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.domicile.id !== null) {
                Domicile.update(vm.domicile, onSaveSuccess, onSaveError);
            } else {
                Domicile.save(vm.domicile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:domicileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
