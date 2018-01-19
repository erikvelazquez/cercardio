(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocialHealthInsuranceDialogController', SocialHealthInsuranceDialogController);

    SocialHealthInsuranceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SocialHealthInsurance'];

    function SocialHealthInsuranceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SocialHealthInsurance) {
        var vm = this;

        vm.socialHealthInsurance = entity;
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
            if (vm.socialHealthInsurance.id !== null) {
                SocialHealthInsurance.update(vm.socialHealthInsurance, onSaveSuccess, onSaveError);
            } else {
                SocialHealthInsurance.save(vm.socialHealthInsurance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:socialHealthInsuranceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
